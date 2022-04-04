package tests;

import static org.assertj.core.api.Assertions.assertThat;

import platform.qa.cucumber.TestContext;
import platform.qa.enums.Context;
import platform.qa.steps.RegulationsStepDefinitions;

import java.io.File;
import java.util.List;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Disabled
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
    void getFormsFromBpmnFiles() {
        regulationsStepDefinitions.verifyRegistryRegulationsLoad();
        regulationsStepDefinitions.getFormsFromBpmnFiles();
        List<String> forms = (List<String>) testContext.getScenarioContext().getContext(Context.BPMN_FORM_KEY_LIST);
        assertThat(forms).hasSizeGreaterThan(0);
        assertThat(forms).isNotEmpty();
    }


    @Test
    void getDeployedFormsFromProvider() {
        regulationsStepDefinitions.getDeployedFormsFromProvider();
        List<String> forms =
                (List<String>) testContext.getScenarioContext().getContext(Context.API_FORM_KEY_LIST);
        assertThat(forms).hasSizeGreaterThan(0);
        assertThat(forms).isNotEmpty();
    }

}