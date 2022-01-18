package platform.qa.steps;

import static org.assertj.core.api.Assertions.assertThat;

import io.cucumber.java.uk.Дано;
import io.cucumber.java.uk.Тоді;
import lombok.NonNull;
import platform.qa.base.BaseSteps;
import platform.qa.base.FileUtils;
import platform.qa.cucumber.TestContext;
import platform.qa.db.TableInfoDb;
import platform.qa.enums.Context;

import org.apache.log4j.Logger;

public class DatabaseStepDefinitions extends BaseSteps {
    private static final Logger LOG = Logger.getLogger(DatabaseStepDefinitions.class);
    private TestContext testContext;

    public DatabaseStepDefinitions(TestContext testContext) {
        this.testContext = testContext;
    }

    @Дано("розгорнута модель даних з переліком таблиць та згенерованими запитами доступу та пошуку даних")
    public void verifyDataFactoryInit() {
        assertThat(config.getDataFactoryService().getUrl())
                .as("Модель даних не розгорнута!!!")
                .isNotNull();
    }

    @Тоді("виконується запит до бази даних з файлу {string}")
    public void executeQueryFromFile(@NonNull String selectFileName) {
        String selectQuery = FileUtils.readFromFile("src/test/resources/data/queries/", selectFileName);
        LOG.info("Execute query: " + selectQuery);
        var result = new TableInfoDb(getPrimarySourceDb()).waitAndGetEntity(selectQuery);
        testContext.getScenarioContext().setContext(Context.DB_RESULT_LIST, result);
    }

    @Тоді("виконується запит до бази даних:")
    public void executeQuery(@NonNull String selectQuery) {
        LOG.info("Execute query: " + selectQuery);
        var result = new TableInfoDb(getPrimarySourceDb()).waitAndGetEntity(selectQuery);
        testContext.getScenarioContext().setContext(Context.DB_RESULT_LIST, result);
    }

}
