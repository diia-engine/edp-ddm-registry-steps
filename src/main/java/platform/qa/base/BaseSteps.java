package platform.qa.base;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import platform.qa.base.Config;
import platform.qa.config.ConfigProvider;
import platform.qa.db.DataSourceInitializer;
import platform.qa.reporting.filters.RequestFilter;

import javax.sql.DataSource;

public abstract class BaseSteps {

    protected static final String dbSchemaName = "registry";
    protected static Config config = (Config) ConfigProvider.getInstance().getConfig(Config.class);

    protected static DataSource primarySourceDb;

    static {
        RestAssured.defaultParser = Parser.JSON;
        RestAssured.registerParser("text/plain", Parser.JSON);
        primarySourceDb = DataSourceInitializer.getSource(config.getPrimaryDb(), dbSchemaName);
    }
}
