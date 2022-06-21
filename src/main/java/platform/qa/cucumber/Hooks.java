package platform.qa.cucumber;


import static platform.qa.base.providers.WebDriverProvider.closeWebDriver;

import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import platform.qa.base.utils.Screenshot;

public class Hooks {

    @After
    public void closeDriver(Scenario scenario) {
        if (scenario.isFailed()) {
            Screenshot.takeScreenshot();
        }
        closeWebDriver();
    }
}
