/*
 * Copyright 2022 EPAM Systems.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package platform.qa.officer.pages.components;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;
import static org.openqa.selenium.By.xpath;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.invisibilityOf;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfAllElements;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import platform.qa.base.BasePage;

import java.util.List;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;

@Log4j2
public class Select extends BasePage {

    private String selectDropdownButtonPath = "//label[text()[contains(.,\"%s\")]]/following-sibling::div//button[@title='Open']";

    @FindBy(xpath = "//ul[@role='listbox']")
    private WebElement selectTable;

    @FindBy(xpath = "//ul[@role='listbox']/li[@role='option']")
    private List<WebElement> selectItems;

    public Select() {
        loadingPage();
        loadingComponents();
    }

    @SneakyThrows
    public void selectItemFromDropDown(String itemName, String itemValue) {
        var selectButton = driver.findElement(xpath(format(selectDropdownButtonPath, itemName)));
        wait.until(elementToBeClickable(selectButton))
                .click();
        waitDropdownLoaded(itemValue);
        scrollToItem(itemValue);
        selectItem(itemValue);
        wait.until(invisibilityOf(selectTable));
    }

    private void selectItem(String itemValue) {
        getDefaultWebDriverWait()
                .ignoring(StaleElementReferenceException.class)
                .ignoring(NoSuchElementException.class)
                .until((ExpectedCondition<WebElement>) driver -> {
                    log.info("Start to select item by value = " + itemValue);
                    WebElement item = getItemByText(itemValue);
                    item.click();
                    return item;
                });
    }

    private void scrollToItem(String itemValue) {
        getDefaultWebDriverWait()
                .ignoring(StaleElementReferenceException.class)
                .ignoring(NoSuchElementException.class)
                .until((ExpectedCondition<WebElement>) driver -> {
                    log.info("Start to scroll to select item!");
                    var selectItem = getItemByText(itemValue);
                    ((ChromeDriver) requireNonNull(driver)).executeScript("arguments[0].scrollIntoView(true);",
                            selectItem);
                    return selectItem;
                });
    }

    private WebElement getItemByText(String itemValue) {
        return selectItems.stream()
                .filter(item -> item.getText().startsWith(itemValue))
                .findFirst().orElseThrow();
    }

    private void waitDropdownLoaded(String itemValue) {
        wait.ignoring(StaleElementReferenceException.class).until(visibilityOf(selectTable));
        wait.ignoring(StaleElementReferenceException.class).until(visibilityOfAllElements(selectItems));
        wait.ignoring(StaleElementReferenceException.class).until((ExpectedCondition<Boolean>) driver -> selectItems.stream()
                .anyMatch(item -> item.getText().startsWith(itemValue)));
    }
}