package platform.qa.officer.pages.components;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;
import static com.codeborne.selenide.Selenide.switchTo;
import static org.assertj.core.api.Assertions.assertThat;
import static org.openqa.selenium.By.xpath;

import io.qameta.allure.Step;
import platform.qa.base.BasePage;

import java.io.File;
import java.time.Duration;
import org.apache.commons.io.FilenameUtils;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

public class CesWidget extends BasePage {
    public SelenideElement signIframe = $(xpath("//iframe[@id='sign-widget']"));
    public SelenideElement signIframeTitle = $(xpath("//h1[@id='titleLabel']"));
    public SelenideElement providerSelect = $(xpath("//select[@id='pkCASelect']"));
    public SelenideElement keyInput = $(xpath("//input[@id='pkReadFileTextField']"));
    public SelenideElement readFileInput = $(xpath("//input[@id='pkReadFileInput']"));
    public SelenideElement keyPasswordInput = $(xpath("//input[@id='pkReadFilePasswordTextField']"));
    public SelenideElement readKeyButton = $(xpath("//div[@id='pkReadFileButton']"));
    public SelenideElement signKeyButton = $(xpath("//button[@data-xpath='signButton']"));

    private static final String PATH_TO_FILE = "src/test/resources/files/";

    @Step("Зчитування ключа на формі КЕП")
    public CesWidget uploadCustomKey(String key, String password, String provider) {
        checkReadKeyFormFields();
        uploadAndReadKey(key, password, provider);
        return page(new CesWidget());
    }

    @Step("Перевірка полів форми завантаження ключа КЕП")
    public CesWidget checkReadKeyFormFields() {
        signIframe.shouldBe(exist);
        switchTo().frame(signIframe);
        signIframeTitle.should(Condition.text("Зчитування особистого ключа"), Duration.ofMillis(60000));
        providerSelect.shouldBe(Condition.enabled);
        keyInput.shouldBe(Condition.enabled);
        keyPasswordInput.shouldBe(Condition.disabled);
        assertThat(readKeyButton.getAttribute("disabled")).isEqualTo("true");

        return page(new CesWidget());
    }

    @Step("Завантаження особистого ключа КЕП зі считуванням")
    public void uploadAndReadKey(String key, String password, String provider) {
        providerSelect.selectOptionContainingText(provider);
        uploadKey(key);
        keyPasswordInput.sendKeys(password);
        clickReadKeyButton();
        switchTo().defaultContent();
    }

    @Step("Завантаження особистого ключа КЕП")
    public void uploadKey(String key) {
        File file = new File(PATH_TO_FILE.concat("keys/"), FilenameUtils.getName(key));
        readFileInput.uploadFile(file);
    }

    @Step("Натискання кнопки 'Зчитати ключ'")
    public void clickReadKeyButton() {
        readKeyButton.shouldBe(visible).shouldBe(enabled);
        clickElement(readKeyButton);
    }

    @Step("Підписання особистого ключа КЕП")
    public void signKey() {
        signKeyButton.should(visible, Duration.ofMillis(30000)).shouldBe(Condition.enabled).click();
    }
}
