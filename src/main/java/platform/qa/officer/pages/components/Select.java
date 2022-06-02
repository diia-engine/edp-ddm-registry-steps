package platform.qa.officer.pages.components;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.empty;
import static com.codeborne.selenide.Condition.exactTextCaseSensitive;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static java.lang.String.format;
import static org.openqa.selenium.By.xpath;

import io.qameta.allure.Step;
import org.openqa.selenium.Keys;
import platform.qa.base.BasePage;

public class Select extends BasePage {

    private final String selectDropdownPath = "//label[text()[contains(.,'%s')]]/following-sibling::div//input[@type='text']";

    public Select() {
        loadingPage();
        loadingComponents();
    }

    @Step("Вибір значення з селект компоненту {filedName}: {fieldData}")
    public void selectItemFromDropDown(String itemName, String itemValue) {
        String selectXPath = getSelectXPath(itemName);
        openDropDown(selectXPath);
        $x(selectXPath)
                .setValue(itemValue)
                .sendKeys(new Keys[]{Keys.ARROW_DOWN, Keys.ENTER});
        $x(selectXPath).shouldNotBe(empty);
    }

    private void openDropDown(String selectXPath) {
        $(xpath(selectXPath))
                .shouldBe(visible)
                .click();
    }

    private String getSelectXPath(String itemName) {
        return format(selectDropdownPath, itemName);
    }
}
