package platform.qa.steps;

import io.cucumber.java.uk.Коли;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import lombok.NonNull;
import platform.qa.base.BaseSteps;
import platform.qa.common.RestApiClient;
import platform.qa.cucumber.TestContext;
import platform.qa.enums.Context;

import java.util.Map;

public class RestApiStepDefinitions extends BaseSteps {
    private TestContext testContext;

    public RestApiStepDefinitions(TestContext testContext) {
        this.testContext = testContext;
        RestAssured.defaultParser = Parser.JSON;
        RestAssured.registerParser("text/plain", Parser.JSON);
    }

    @Коли("виконується запит пошуку {string} з параметрами")
    public void user_execute_api_with_path_and_parameters(@NonNull String path,
                                                          @NonNull Map<String, String> queryParams) {
        var result = new RestApiClient(config.getDataFactoryService())
                .sendGetWithParams(path, queryParams)
                .extract()
                .response()
                .jsonPath()
                .getList("", Map.class);
        testContext.getScenarioContext().setContext(Context.API_RESULT_LIST, result);
    }
}
