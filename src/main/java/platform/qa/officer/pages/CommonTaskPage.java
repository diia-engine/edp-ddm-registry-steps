package platform.qa.officer.pages;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;
import static org.assertj.core.api.Assertions.assertThat;
import static org.openqa.selenium.By.xpath;

import io.qameta.allure.Step;
import platform.qa.base.BasePage;

import java.util.Arrays;
import org.openqa.selenium.By;
import com.codeborne.selenide.SelenideElement;

public abstract class CommonTaskPage extends BasePage {
    protected By taskNameBy = xpath("//div[contains(@class, 'root')]/h2");
    protected SelenideElement submitButton = $(xpath("//button[contains(@name, 'data[submit]')]"));

    @Step("Перевірка що назва задачі на формі одна з {taskNamesExpected}")
    public <T extends CommonTaskPage> T checkTaskName(Class<T> clazz, String... taskNamesExpected) {
        loadingPage();
        loadingComponents();
        $(taskNameBy).shouldBe(visible);
        assertThat($(taskNameBy).getText()).as("Page title is not in expected list!")
                .isIn(Arrays.asList(taskNamesExpected));
        return page(clazz);
    }

    @Step("Підтвердження внесених даних")
    public void submitForm() {
        submitButton
                .shouldBe(enabled)
                .click();
    }
}
