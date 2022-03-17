package platform.qa.officer.pages;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;
import static org.assertj.core.api.Assertions.assertThat;
import static org.openqa.selenium.By.xpath;

import io.qameta.allure.Step;
import platform.qa.officer.pages.components.TableComponent;

import org.openqa.selenium.By;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

public class MyTasksPage extends OfficerBasePage {

    private final String myTasksTextUa = "Мої задачі";
    private final SelenideElement provisionedTasksTab = $(By.xpath("//button[@data-xpath='tabsButton-closed']"));
    private final SelenideElement myTasksHeader = $(xpath("//div[@data-xpath='header']/following-sibling::div//h1"));
    private final SelenideElement timedCreatedNotification = $(xpath("//div[@data-testid='timed-notification']"));

    public MyTasksPage() {
        loadingPage();
        loadingComponents();
        checkMyTasksHeader();
    }

    @Step("Перевірка присутності обраної задачі за назвою у переліку завершених задач {taskName}")
    public MyTasksPage checkTaskExistsByTaskName(String taskName) {
        new TableComponent()
                .getRowFromTableByTaskName(taskName)
                .shouldHave(sizeGreaterThan(0)
                        .because(String.format("Задача \"%s\" відсутня у черзі задач", taskName)));
        return page(new MyTasksPage());
    }

    @Step("Сторінка має хедер 'Мої задачі'")
    public MyTasksPage checkMyTasksHeader() {
        myTasksHeader.shouldHave(Condition.text(myTasksTextUa));
        return this;
    }

    @Step("Клік по вкладці 'Виконані задачі'")
    public MyTasksPage clickOnProvisionedTasksTab() {
        provisionedTasksTab.shouldBe(visible).click();
        return page(new MyTasksPage());
    }

    @Step("Перевірка вспливаючого повідомлення")
    public MyTasksPage checkNotificationMessage(String taskName) {
        String message = String.format("Вітаємо!\nЗадача “%s” виконана успішно!", taskName);
        timedCreatedNotification.shouldBe(visible);
        assertThat(timedCreatedNotification.getText()).as("Поточне повідомлення не збігається з очікуваним").isEqualTo(message);
        return page(new MyTasksPage());
    }
}
