package platform.qa.cucumber;

import static com.codeborne.selenide.Selenide.closeWebDriver;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.Scenario;
import io.qameta.allure.selenide.AllureSelenide;
import platform.qa.configuration.RunUITestConfiguration;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;

public class Hooks {

    protected static RunUITestConfiguration runUITestConfig = RunUITestConfiguration.getInstance();

    @BeforeAll
    public static void globalDriverSetup() {
        SelenideLogger.addListener("AllureSelenide",
                new AllureSelenide()
                        .screenshots(true)
                        .savePageSource(false));
        Configuration.browserSize = "1920x1080";
        Configuration.screenshots = true;
        Configuration.browserCapabilities.setCapability("enableVNC", true);
        Configuration.timeout = 15000;
        if (runUITestConfig.isRemoteRunEnabled()) {
            Configuration.headless = true;
            Configuration.browserSize = "1920x1080";
        }
    }

    @Before
    public void setScenarioName(Scenario scenario) {
        Configuration.browserCapabilities.setCapability("name", scenario.getName());
    }

    @After
    public void closeDriver() {
        closeWebDriver();
    }
}
