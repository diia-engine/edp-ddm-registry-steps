package platform.qa.steps;

import static org.assertj.core.api.Assertions.assertThat;

import io.cucumber.java.uk.Дано;
import io.cucumber.java.uk.Коли;
import io.cucumber.java.uk.Тоді;
import platform.qa.base.Config;
import platform.qa.base.Mapper;
import platform.qa.clients.api.RestApiClient;
import platform.qa.clients.git.JgitClient;
import platform.qa.config.ConfigProvider;
import platform.qa.cucumber.TestContext;
import platform.qa.entities.BusinessProcess;
import platform.qa.entities.Repository;
import platform.qa.enums.Context;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.Assertions;

public class RegulationsStepDefinitions {
    private static Logger logger = Logger.getLogger(RegulationsStepDefinitions.class);
    private Config config = (Config) ConfigProvider.getInstance().getConfig(Config.class);
    private Repository gerritRepo = new Repository(config.getGerrit(), "registry-regulations", "master");
    private TestContext testContext;

    public RegulationsStepDefinitions(TestContext testContext) {
        this.testContext = testContext;
    }

    @Дано("розгорнутий регламент реєстру з необхідними процесами")
    public void verifyRegistryRegulationsLoad() {
        List<File> bpmnFiles;
        Object bpmn = testContext.getScenarioContext().getContext(Context.BPMN_FILE_NAMES);
        if (!Objects.isNull(bpmn)) {
            bpmnFiles = (List<File>) bpmn;
        } else {
            bpmnFiles = getBpmnFilesFromGerritFolder("bpmn", ".bpmn");
            testContext.getScenarioContext().setContext(Context.BPMN_FILE_NAMES, bpmnFiles);
        }
        assertThat(bpmnFiles).as("Регламент не розгорнувся:").hasSizeGreaterThan(0);
    }

    @Коли("виконується пошук необхідних форм для розгорнутих процесів")
    public void getFormsFromBpmnFiles() {
        List<File> bpmnFiles = (List<File>) testContext.getScenarioContext().getContext(Context.BPMN_FILE_NAMES);
        assertThat(bpmnFiles).as("В розгорнутому регламенті немає списку bpmn файлів процесів:").hasSizeGreaterThan(0);

        List<String> formKeys = getFormKeysFromBpmnFiles(bpmnFiles);
        testContext.getScenarioContext().setContext(Context.BPMN_FORM_KEY_LIST, formKeys);
    }

    @Коли("виконується пошук розгорнутих форм на оточенні")
    public void getDeployedFormsFromProvider() {
        logger.info("Start getting forms from Form Provider!");
        List<Map> deployedForms = new RestApiClient(config.getFormManagementProvider().getUrl(),
                Map.of("X-Access-Token", config.getDataUser().getToken()))
                .get("/form")
                .jsonPath()
                .getList("", Map.class);
        List<String> formTitles =
                deployedForms.stream().map(form -> form.get("name").toString()).collect(Collectors.toList());
        testContext.getScenarioContext().setContext(Context.API_FORM_KEY_LIST, formTitles);
    }

    @Тоді("виконується перевірка, що всі необхідні форми процесів розгорнуті на оточенні")
    public void checkIfAllBpmnFormsWereDeployed() {
        List<String> expectedForms =
                (List<String>) testContext.getScenarioContext().getContext(Context.BPMN_FORM_KEY_LIST);
        List<String> actualForms =
                (List<String>) testContext.getScenarioContext().getContext(Context.API_FORM_KEY_LIST);
        assertThat(actualForms).as("Кількість розгорнутих форм на оточенні менша ніж в registry-regulations "
                + "репозиторії:").hasSizeGreaterThanOrEqualTo(expectedForms.size());
        assertThat(actualForms).as("Форми розгорнені на оточенні не співпадають з усіма необхідними в "
                + "registry-regulations репозиторії").containsAll(expectedForms);
    }

    private List<String> getFormKeysFromBpmnFiles(List<File> bpmnFiles) {
        List<BusinessProcess> businessProcesses = bpmnFiles.stream()
                .map(file -> Mapper.convertPartOfXmlFileToObject(file, "process", BusinessProcess.class))
                .collect(Collectors.toList());
        assertThat(businessProcesses).as("Файлів процесів немає в папці target:").hasSizeGreaterThan(0);

        return businessProcesses.stream()
                .flatMap(bp -> Optional.ofNullable(bp.getUserTask()).stream()
                        .map(userTasks -> userTasks.stream()
                                .map(BusinessProcess.UserTask::getFormKey)
                                .distinct()
                                .collect(Collectors.toList()))
                ).findFirst().orElseThrow();
    }

    private List<File> getBpmnFilesFromGerritFolder(String folder, String fileExtension) {
        List<List<File>> filesFromFolder = new JgitClient(gerritRepo).getFilesFromFolder(folder);
        Assertions.assertNotNull(filesFromFolder);
        return filesFromFolder.get(0).stream()
                .filter(file -> file.getName().endsWith(fileExtension))
                .collect(Collectors.toList());
    }
}
