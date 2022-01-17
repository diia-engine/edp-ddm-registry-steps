package tests;

import platform.qa.config.ConfigProvider;
import platform.qa.config.RegistryConfig;

import org.junit.jupiter.api.BeforeAll;

public abstract class BaseTest {

    protected static RegistryConfig config;

    @BeforeAll
    public static void setup() {
        config = (RegistryConfig) ConfigProvider.getInstance().getConfig(RegistryConfig.class);

    }
}
