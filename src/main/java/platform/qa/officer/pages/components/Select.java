package platform.qa.officer.pages.components;

import static java.lang.String.format;
import static org.openqa.selenium.By.xpath;
import static org.openqa.selenium.Keys.ARROW_DOWN;
import static org.openqa.selenium.Keys.ENTER;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfAllElements;

import lombok.SneakyThrows;
import platform.qa.base.BasePage;

import java.util.List;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class Select extends BasePage {

    private final String selectDropdownPath = "//label[text()[contains(.,'%s')"
            + "]]/following-sibling::div//input[@type='text']";

    @FindBy(xpath = "//ul[contains(@class,'MuiAutocomplete-listbox')]/li")
    private List<WebElement> selectItems;

    public Select() {
        loadingPage();
        loadingComponents();
    }

    @SneakyThrows
    public void selectItemFromDropDown(String itemName, String itemValue) {
        String selectXPath = getSelectXPath(itemName);
        WebElement select = driver.findElement(xpath(selectXPath));
        wait.until(ExpectedConditions.elementToBeClickable(select))
                .click();
        select.sendKeys(itemValue);
        wait.until(visibilityOfAllElements(selectItems));
        wait.until((ExpectedCondition<Boolean>) driver -> selectItems.stream().noneMatch(item -> item.getText().isEmpty()));
        select.sendKeys(ARROW_DOWN, ENTER);
        wait.until((ExpectedCondition<Boolean>) driver -> !select.getAttribute("value").isEmpty());
    }

    private String getSelectXPath(String itemName) {
        return format(selectDropdownPath, itemName);
    }
}
