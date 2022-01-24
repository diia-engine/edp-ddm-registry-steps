package platform.qa.steps;

import static org.assertj.core.api.Assertions.assertThat;

import platform.qa.base.Config;
import platform.qa.base.FileUtils;
import platform.qa.base.Mapper;
import platform.qa.clients.git.JgitClient;
import platform.qa.config.ConfigProvider;
import platform.qa.cucumber.TestContext;
import platform.qa.entities.BusinessProcess;
import platform.qa.entities.Repository;
import platform.qa.enums.Context;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class RegulationsStepDefinitions {
    private Config config = (Config) ConfigProvider.getInstance().getConfig(Config.class);
    private Repository gerritRepo = new Repository(config.getGerrit(), "registry-regulations", "master");
    private TestContext testContext;

    public RegulationsStepDefinitions(TestContext testContext) {
        this.testContext = testContext;
    }

    public void verifyRegistryRegulationsLoad() {
        Object bpmn = testContext.getScenarioContext().getContext(Context.BPMN_FILE_NAMES);
        List<String> bpmnFileNames = new LinkedList<>();
        if (!Objects.isNull(bpmn)) {
            bpmnFileNames = (List<String>) bpmn;
        } else {
            var bpmnFiles = new JgitClient(gerritRepo).getFilesFromFolder("bpmn");
            testContext.getScenarioContext().setContext(Context.BPMN_FILE_NAMES, bpmnFiles);
        }
        assertThat(bpmnFileNames).as("Регламент не розгорнувся").hasSizeGreaterThan(0);
    }

    public void loadRegistryRegulations() {

        List<BusinessProcess> businessProcesses = FileUtils.readFilesToObjectList("target/bpmn",
                BusinessProcess.class, Mapper.getXmlObjectMapper());

    }
}
