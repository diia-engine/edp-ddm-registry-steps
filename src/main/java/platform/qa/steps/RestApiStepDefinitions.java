package platform.qa.steps;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.hamcrest.Matchers.isIn;

import io.cucumber.java.uk.Коли;
import io.cucumber.java.uk.Тоді;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import lombok.NonNull;
import platform.qa.base.Config;
import platform.qa.clients.api.DataFactoryClient;
import platform.qa.config.ConfigProvider;
import platform.qa.cucumber.TestContext;
import platform.qa.enums.Context;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;

public class RestApiStepDefinitions {
    private Config config = (Config) ConfigProvider.getInstance().getConfig(Config.class);
    private TestContext testContext;

    public RestApiStepDefinitions(TestContext testContext) {
        this.testContext = testContext;
        RestAssured.defaultParser = Parser.JSON;
        RestAssured.registerParser("text/plain", Parser.JSON);
    }

    @Коли("виконується запит пошуку {string} з параметрами")
    public void executeGetApiWithParameters(@NonNull String path,
                                            @NonNull Map<String, String> queryParams) {
        var result = new DataFactoryClient(config.getDataFactoryService())
                .sendGetWithParams(path, queryParams)
                .extract()
                .response()
                .jsonPath()
                .getList("", Map.class);
        testContext.getScenarioContext().setContext(Context.API_RESULT_LIST, result);
    }

    @Коли("виконується запит пошуку {string} без параметрів")
    public void executeGetApiWithoutParameters(String path) {
        var result = new DataFactoryClient(config.getDataFactoryService())
                .get(path)
                .then()
                .statusCode(isIn(getSuccessStatuses()))
                .extract()
                .response()
                .jsonPath()
                .getList("", Map.class);
        testContext.getScenarioContext().setContext(Context.API_RESULT_LIST, result);
    }

    @Тоді("результат запиту містить наступні значення {string} у полі {string}")
    public void verifyApiHasValuesInField(String fieldValue, String fieldName) {
        var actualResult = (List<Map>) testContext.getScenarioContext().getContext(Context.API_RESULT_LIST);
        assertThatJson(actualResult).as("Такого поля не існує в json-і:")
                .inPath("$.." + fieldName).isPresent();
        assertThatJson(actualResult).as("Дані в полі не співпадають:")
                .inPath("$.." + fieldName).isArray().contains(fieldValue);
    }

    private List<Integer> getSuccessStatuses() {
        return Arrays.stream(HttpStatus.values())
                .filter(httpStatus -> httpStatus.toString().startsWith("20"))
                .map(HttpStatus::value)
                .collect(Collectors.toList());
    }
}
