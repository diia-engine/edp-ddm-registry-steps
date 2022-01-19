package platform.qa.steps;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static net.javacrumbs.jsonunit.core.Option.IGNORING_EXTRA_FIELDS;
import static org.assertj.core.api.Assertions.assertThat;

import io.cucumber.java.uk.Дано;
import io.cucumber.java.uk.Тоді;
import platform.qa.base.Config;
import platform.qa.base.FileUtils;
import platform.qa.config.ConfigProvider;
import platform.qa.cucumber.TestContext;
import platform.qa.enums.Context;

import java.util.List;
import java.util.Map;

public class DataModelStepDefinitions {
    private static Config config = (Config) ConfigProvider.getInstance().getConfig(Config.class);
    private TestContext testContext;

    public DataModelStepDefinitions(TestContext testContext) {
        this.testContext = testContext;
    }

    @Дано("розгорнута модель даних з переліком таблиць та згенерованими запитами доступу та пошуку даних")
    public void verifyDataFactoryInit() {
        assertThat(config.getDataFactoryService().getUrl())
                .as("Модель даних не розгорнута!!!")
                .isNotNull();
    }

    @Тоді("дата модель повертає точно заданий json нижче:$")
    public void verifyDataModelReturnJsonWithData(String expectedJsonText) {
        var actualResult = (List<Map>) testContext.getScenarioContext().getContext(Context.API_RESULT_LIST);
        assertThatJson(actualResult).as("Дані не співпадають:").isEqualTo(expectedJsonText);
    }

    @Тоді("дата модель повертає json з файлу {string}")
    public void verifyDataModelReturnJsonFromFileWithData(String jsonFileName) {
        var actualResult = (List<Map>) testContext.getScenarioContext().getContext(Context.API_RESULT_LIST);
        String expectedJsonText = FileUtils.readFromFile("src/test/resources/data/json/", jsonFileName);
        assertThatJson(actualResult).as("Дані не співпадають:").isEqualTo(expectedJsonText);
    }

    @Тоді("дата модель повертає json, який містить точно наступні дані, ігноруючі невказані:$")
    public void verifyDataModelReturnJsonWithDataFromExpected(String expectedJsonText) {
        var actualResult = (List<Map>) testContext.getScenarioContext().getContext(Context.API_RESULT_LIST);
        assertThatJson(actualResult).as("Дані не співпадають:")
                .when(IGNORING_EXTRA_FIELDS).isEqualTo(expectedJsonText);
    }

    @Тоді("дата модель повертає точно заданий json з файлу {string}, ігноруючі невказані")
    public void verifyDataModelReturnJsonFromFileWithDataFromExpected(String jsonFileName) {
        var actualResult = (List<Map>) testContext.getScenarioContext().getContext(Context.API_RESULT_LIST);
        String expectedJsonText = FileUtils.readFromFile("src/test/resources/data/json/", jsonFileName);
        assertThatJson(actualResult).as("Дані не співпадають:")
                .when(IGNORING_EXTRA_FIELDS).isEqualTo(expectedJsonText);
    }
}
