package platform.qa.officer.pages.components;

import lombok.SneakyThrows;
import org.openqa.selenium.WebElement;
import platform.qa.base.BasePage;

import static java.lang.String.format;
import static org.openqa.selenium.By.xpath;
import static org.openqa.selenium.Keys.ARROW_DOWN;
import static org.openqa.selenium.Keys.ENTER;

public class Select extends BasePage {

    private final String selectInputPath = "//label[text()[contains(.,\"%s\")]]" +
            "/following-sibling::div//input[@type='text']";

    private final String selectDropdownPath = "//label[text()[contains(.,\"%s\")]]" +
            "/parent::div//div[@role='combobox']";

    public Select() {
        loadingPage();
        loadingComponents();
    }

    @SneakyThrows
    public void selectItemFromDropDown(String itemName, String itemValue) {
        String selectXPath = getSelectInputXPath(itemName);
        WebElement selectInput = driver.findElement(xpath(selectXPath));
        selectInput.click();
        Thread.sleep(1000); // needs to be replaced in the future
        selectInput.sendKeys(itemValue);
        Thread.sleep(1000); // needs to be replaced in the future
        selectInput.sendKeys(ARROW_DOWN, ENTER);
    }

    private String getSelectInputXPath(String itemName) {
        return format(selectInputPath, itemName);
    }

    private String getSelectDropdownXPath(String itemName) {
        return format(selectDropdownPath, itemName);
    }
}
