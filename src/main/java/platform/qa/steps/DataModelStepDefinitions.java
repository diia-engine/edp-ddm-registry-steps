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
    private Config config = (Config) ConfigProvider.getInstance().getConfig(Config.class);
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
    public void verifyDataModelReturnJsonFromFileWithData(String jsonFilePath) {
        var actualResult = (List<Map>) testContext.getScenarioContext().getContext(Context.API_RESULT_LIST);
        String filePath = getFilePath(jsonFilePath);
        String jsonFileName = getJsonFileName(jsonFilePath);
        String expectedJsonText = FileUtils.readFromFile(filePath, jsonFileName);
        assertThatJson(actualResult).as("Дані не співпадають:").isEqualTo(expectedJsonText);
    }

    @Тоді("дата модель повертає json, який містить точно наступні дані, ігноруючі невказані:$")
    public void verifyDataModelReturnJsonWithDataFromExpected(String expectedJsonText) {
        var actualResult = (List<Map>) testContext.getScenarioContext().getContext(Context.API_RESULT_LIST);
        assertThatJson(actualResult).as("Дані не співпадають:")
                .when(IGNORING_EXTRA_FIELDS).isEqualTo(expectedJsonText);
    }

    @Тоді("дата модель повертає точно заданий json з файлу {string}, ігноруючі невказані")
    public void verifyDataModelReturnJsonFromFileWithDataFromExpected(String jsonFilePath) {
        var actualResult = (List<Map>) testContext.getScenarioContext().getContext(Context.API_RESULT_LIST);
        String filePath = getFilePath(jsonFilePath);
        String jsonFileName = getJsonFileName(jsonFilePath);
        String expectedJsonText = FileUtils.readFromFile(filePath, jsonFileName);
        assertThatJson(actualResult).as("Дані не співпадають:")
                .when(IGNORING_EXTRA_FIELDS).isEqualTo(expectedJsonText);
    }

    private String getJsonFileName(String jsonFilePath) {
        String jsonFileName = jsonFilePath;
        if (jsonFilePath.contains("/")) {
            jsonFileName = jsonFilePath.substring(jsonFilePath.lastIndexOf("/") + 1);
        }
        return jsonFileName;
    }

    private String getFilePath(String jsonFilePath) {
        String endPath = "";
        if (jsonFilePath.contains("/")) {
            String endPathTmp = jsonFilePath.substring(0, jsonFilePath.lastIndexOf("/"));
            endPath = endPathTmp.startsWith("/") ? endPathTmp.substring(1) : endPathTmp;
        }
        return "src/test/resources/data/json/" + endPath;
    }
}
