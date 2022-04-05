package tests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import platform.qa.cucumber.TestContext;
import platform.qa.enums.Context;
import platform.qa.steps.RegulationsStepDefinitions;

import java.io.File;
import java.util.List;
import org.junit.jupiter.api.Test;

class RegulationsStepDefinitionsTest extends BaseTest {

    private static final TestContext testContext = new TestContext();
    private RegulationsStepDefinitions regulationsStepDefinitions = new RegulationsStepDefinitions(testContext);

    @Test
    void verifyRegistryRegulationsLoad() {
        regulationsStepDefinitions.verifyRegistryRegulationsLoad();
        List<File> bpmnFiles = (List<File>) testContext.getScenarioContext().getContext(Context.BPMN_FILE_NAMES);
        assertThat(bpmnFiles).hasSizeGreaterThan(0);
        assertThat(bpmnFiles).isNotEmpty();
    }

    @Test
    void getProcessesAndForms() {
        regulationsStepDefinitions.verifyRegistryRegulationsLoad();
        regulationsStepDefinitions.getProcessesAndForms();

        List<String> formKeys = (List<String>) testContext.getScenarioContext().getContext(Context.BPMN_FORM_KEY_LIST);
        List<String> deployedFormKeys = (List<String>) testContext.getScenarioContext().getContext(Context.API_FORM_KEY_LIST);
        List<String> processNames = (List<String>) testContext.getScenarioContext().getContext(Context.BPMN_PROCESS_NAME_LIST);
        List<String> deployedProcessNames = (List<String>) testContext.getScenarioContext().getContext(Context.API_PROCESS_NAME_LIST);

        assertSoftly(softly -> {
            softly.assertThat(formKeys).hasSizeGreaterThan(0);
            softly.assertThat(formKeys).isNotEmpty();

            softly.assertThat(deployedFormKeys).hasSizeGreaterThan(0);
            softly.assertThat(deployedFormKeys).isNotEmpty();

            softly.assertThat(processNames).hasSizeGreaterThan(0);
            softly.assertThat(processNames).isNotEmpty();

            softly.assertThat(deployedProcessNames).hasSizeGreaterThan(0);
            softly.assertThat(deployedProcessNames).isNotEmpty();
        });
    }
}