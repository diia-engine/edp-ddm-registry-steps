package platform.qa.officer.pages.components;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.empty;
import static com.codeborne.selenide.Condition.exactTextCaseSensitive;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static java.lang.String.format;
import static org.openqa.selenium.By.xpath;

import io.qameta.allure.Step;
import platform.qa.base.BasePage;

public class SelectComponent extends BasePage {

    private final String selectDropdownPath = "//label[text()[contains(.,'%s')"
            + "]]/following-sibling::div//select/parent::div[contains(@class, 'dropdown')]";
    private String choicesListXPath = "//following-sibling::div[contains(@class,'choices__list--dropdown')]";
    private String itemsXPath = "//div[contains(@id,'item-choice')]/span";

    public SelectComponent() {
        loadingPage();
        loadingComponents();
    }

    @Step("Вибір значення з селект компоненту {filedName}: {fieldData}")
    public void selectItemFromDropDown(String itemName, String itemValue) {
        String selectXPath = getSelectXPath(itemName);
        openDropDown(selectXPath);
        $$(xpath(selectXPath.concat(choicesListXPath).concat(itemsXPath)))
                .filter(exactTextCaseSensitive(itemValue))
                .shouldHave(sizeGreaterThan(0))
                .first()
                .shouldNotBe(empty)
                .click();
    }

    private void openDropDown(String selectXPath) {
        $(xpath(selectXPath))
                .shouldBe(visible)
                .click();

        $(xpath(selectXPath.concat(choicesListXPath)))
                .shouldHave(attribute("aria-expanded", "true"));
    }

    private String getSelectXPath(String itemName) {
        return format(selectDropdownPath, itemName);
    }
}
