package platform.qa.steps;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

import io.cucumber.java.uk.Дано;
import io.cucumber.java.uk.Коли;
import io.cucumber.java.uk.Та;
import io.cucumber.java.uk.Тоді;
import lombok.NonNull;
import platform.qa.base.BaseSteps;
import platform.qa.base.FileUtils;
import platform.qa.common.RestApiClient;
import platform.qa.cucumber.TestContext;
import platform.qa.db.TableInfoDb;
import platform.qa.enums.Context;

import java.util.List;
import java.util.Map;

public class SearchConditionsStepDefinitions extends BaseSteps {
    private final TestContext testContext;

    public SearchConditionsStepDefinitions(TestContext testContext) {
        this.testContext = testContext;
    }

    @Дано("розгорнута модель даних з переліком таблиць та згенерованими запитами доступу та пошуку даних")
    public void data_factory_initialize_verification() {
        assertThat(config.getDataFactoryService().getUrl())
                .as("Модель даних не розгорнута!!!")
                .isNotNull();
    }

    @Коли("виконується запит пошуку {string} з параметрами")
    public void user_execute_Api_with_Path_and_Parameters(@NonNull String path,
                                                          @NonNull Map<String, String> queryParams) {
        var result = new RestApiClient(config.getDataFactoryService())
                .sendGetWithParams(path, queryParams)
                .extract()
                .response()
                .jsonPath()
                .getList("", Map.class);
        testContext.getScenarioContext().setContext(Context.API_RESULT_LIST, result);
    }

    @Тоді("виконується запит до бази даних з файлу {string}")
    public void user_execute_Query_From_File_with_Parameters(@NonNull String selectFileName) {
        String selectQuery = FileUtils.readFromFile("src/test/resources/data/queries/", selectFileName);
        var result = new TableInfoDb(primarySourceDb).waitAndGetEntity(selectQuery);
        testContext.getScenarioContext().setContext(Context.DB_RESULT_LIST, result);
    }

    @Тоді("виконується запит до бази даних:")
    public void user_execute_Query_with_Parameters(@NonNull String selectQuery) {
        var result = new TableInfoDb(primarySourceDb).waitAndGetEntity(selectQuery);
        testContext.getScenarioContext().setContext(Context.DB_RESULT_LIST, result);
    }

    @Та("звіряється результат запиту до сервісу з даними з бази даних")
    public void compare_Api_and_Select_result() {
        var actualResult = (List<Map>) testContext.getScenarioContext().getContext(Context.API_RESULT_LIST);
        var expectedResult = (List<Map>) testContext.getScenarioContext().getContext(Context.DB_RESULT_LIST);
        assertThat(actualResult).as("Кількість записів не співпадає:").hasSameSizeAs(expectedResult);
        assertThat(actualResult).as("Дані не співпадають:").hasSameElementsAs(expectedResult);
    }


    @Тоді("дата модель повертає наспуний json даних:")
    public void data_model_return_Json_with_Data(String expectedJsonText) {
        var actualResult = (List<Map>) testContext.getScenarioContext().getContext(Context.API_RESULT_LIST);
        assertThatJson(actualResult).as("Дані не співпадають:").isEqualTo(expectedJsonText);
    }

    @Тоді("дата модель повертає json даних описаний в файлі {string}")
    public void data_model_return_json_from_file_with_data(String jsonFileName) {
        var actualResult = (List<Map>) testContext.getScenarioContext().getContext(Context.API_RESULT_LIST);
        String expectedJsonText = FileUtils.readFromFile("src/test/resources/data/json/", jsonFileName);
        assertThatJson(actualResult).as("Дані не співпадають:").isEqualTo(expectedJsonText);
    }

}


