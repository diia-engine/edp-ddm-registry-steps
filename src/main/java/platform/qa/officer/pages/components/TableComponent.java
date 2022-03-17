package platform.qa.officer.pages.components;

import static com.codeborne.selenide.CollectionCondition.allMatch;
import static com.codeborne.selenide.Condition.matchText;
import static com.codeborne.selenide.Selenide.$$;
import static org.openqa.selenium.By.xpath;

import io.qameta.allure.Step;
import platform.qa.base.BasePage;

import org.openqa.selenium.WebElement;
import com.codeborne.selenide.ElementsCollection;

public class TableComponent extends BasePage {

    private ElementsCollection tableRows = $$(xpath("//tbody//tr[contains(@class, 'MuiTableRow-root')]"));

    public TableComponent() {
        loadingPage();
        loadingComponents();
    }

    @Step("Отримання рядку таблиці для вказаної завершеної задачі {taskName}")
    public ElementsCollection getRowFromTableByTaskName(String taskName) {
        return getTableRows().filter(matchText(".*" + taskName + ".*").because("Відсутній рядок в таблиці з текстом: "
                + taskName));
    }

    private ElementsCollection getTableRows() {
        if (tableRows.isEmpty()) {
            return tableRows;
        } else {
            return tableRows.shouldBe(allMatch("Елементи в таблиці недоступні", WebElement::isDisplayed));
        }
    }
}
