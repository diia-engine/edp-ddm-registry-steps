package platform.qa.base;

import static com.codeborne.selenide.Condition.cssValue;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

import io.qameta.allure.Step;
import platform.qa.configuration.MasterConfig;
import platform.qa.configuration.RegistryConfig;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;

public abstract class BasePage {
    protected final RegistryConfig registryConfig = MasterConfig.getInstance().getRegistryConfig();
    protected Logger logger = Logger.getLogger(this.getClass());

    public void openPage(String url) {
        logger.info("Trying to open Login Page");
        open(url);
        logger.info("Login Page was successfully opened!");
    }

    @Step("Очікування відображення сторінки")
    protected void loadingPage() {
        if ($(By.xpath("//*[@data-xpath='loader']"))
                .isDisplayed())
            $(By.xpath("//*[@data-xpath='loader']"))
                    .shouldHave(cssValue("display", "none"), Duration.of(30, ChronoUnit.SECONDS));
    }

    @Step("Очікування прогрузки компонентів")
    protected void loadingComponents() {
        if ($(By.xpath("//*[@data-xpath='component-loader']"))
                .isDisplayed())
            $(By.xpath("//*[@data-xpath='component-loader']"))
                    .shouldHave(cssValue("display", "none"), Duration.of(30, ChronoUnit.SECONDS));
    }

    public void clickElement(SelenideElement element) {
        ((JavascriptExecutor) WebDriverRunner.getWebDriver())
                .executeScript("arguments[0].click();", element);
    }
}
