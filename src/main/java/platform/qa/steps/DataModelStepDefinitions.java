package platform.qa.steps;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

import io.cucumber.java.uk.Та;
import io.cucumber.java.uk.Тоді;
import platform.qa.base.BaseSteps;
import platform.qa.base.FileUtils;
import platform.qa.cucumber.TestContext;
import platform.qa.enums.Context;

import java.util.List;
import java.util.Map;

public class DataModelStepDefinitions extends BaseSteps {
    private TestContext testContext;

    public DataModelStepDefinitions(TestContext testContext) {
        this.testContext = testContext;
    }

    @Тоді("дата модель повертає наспуний json даних:")
    public void data_model_return_json_with_data(String expectedJsonText) {
        var actualResult = (List<Map>) testContext.getScenarioContext().getContext(Context.API_RESULT_LIST);
        assertThatJson(actualResult).as("Дані не співпадають:").isEqualTo(expectedJsonText);
    }

    @Тоді("дата модель повертає json даних описаний в файлі {string}")
    public void data_model_return_json_from_file_with_data(String jsonFileName) {
        var actualResult = (List<Map>) testContext.getScenarioContext().getContext(Context.API_RESULT_LIST);
        String expectedJsonText = FileUtils.readFromFile("src/test/resources/data/json/", jsonFileName);
        assertThatJson(actualResult).as("Дані не співпадають:").isEqualTo(expectedJsonText);
    }

    @Та("дата модель повертає json даних який збігається з відповіддю від запиту пошуку")
    public void compare_api_and_database_result() {
        var actualResult = (List<Map>) testContext.getScenarioContext().getContext(Context.API_RESULT_LIST);
        var expectedResult = (List<Map>) testContext.getScenarioContext().getContext(Context.DB_RESULT_LIST);
        assertThat(actualResult).as("Кількість записів не співпадає:").hasSameSizeAs(expectedResult);
        assertThat(actualResult).as("Дані не співпадають:").hasSameElementsAs(expectedResult);
    }
}
