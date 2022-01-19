package tests;

import platform.qa.base.Config;
import platform.qa.config.ConfigProvider;

import org.junit.jupiter.api.BeforeAll;

public abstract class BaseTest {

    protected static Config config;

    @BeforeAll
    public static void setup() {
        config = (Config) ConfigProvider.getInstance().getConfig(Config.class);

    }
}
