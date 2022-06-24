package platform.qa.base.providers;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import platform.qa.configuration.RunUITestConfiguration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Log4j2
public class WebDriverProvider {
    protected static RunUITestConfiguration runUITestConfig = RunUITestConfiguration.getInstance();
    private static WebDriver instance;

    public static WebDriver getInstance() {
        if (instance == null) {
            instance = init();
        }
        return instance;
    }

    private static WebDriver init() {
        WebDriverManager.chromedriver().setup();
        WebDriver driver;
        driver = getDriver();
        driver.manage().window().maximize();
        return driver;
    }

    private static WebDriver getDriver() {
        if (runUITestConfig.isRemoteRunEnabled()) {
            ChromeOptions options = new ChromeOptions();
            options.setHeadless(true);
            return new ChromeDriver(options);
        } else {
            return new ChromeDriver();
        }
    }

    public static void closeWebDriver() {
        if (instance != null) {
            try {
                instance.quit();
            } catch (Exception exception) {
                log.info("Can not quit browser!!!");
            } finally {
                instance = null;
            }
        }
    }
}
