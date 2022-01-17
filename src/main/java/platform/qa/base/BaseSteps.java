package platform.qa.base;

import platform.qa.config.ConfigProvider;
import platform.qa.db.DataSourceInitializer;

import javax.sql.DataSource;

public abstract class BaseSteps {

    protected static final String dbSchemaName = "registry";
    protected static Config config = (Config) ConfigProvider.getInstance().getConfig(Config.class);

    protected DataSource getPrimarySourceDb() {
        return DataSourceInitializer.getSource(config.getPrimaryDb(), dbSchemaName);
    }
}
