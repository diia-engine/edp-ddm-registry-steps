package tests;

import platform.qa.configuration.MasterConfig;
import platform.qa.configuration.RegistryConfig;

import org.junit.jupiter.api.BeforeAll;

public abstract class BaseTest {

    protected static RegistryConfig registryConfig;

    @BeforeAll
    public static void setup() {
        registryConfig = MasterConfig.getInstance().getRegistryConfig();

    }
}
