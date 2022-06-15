package platform.qa.officer.pages;

import static org.assertj.core.api.Assertions.assertThat;
import static org.openqa.selenium.By.xpath;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

import lombok.SneakyThrows;
import platform.qa.base.BasePage;

import java.util.Arrays;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public abstract class CommonTaskPage extends BasePage {
    protected By taskNameBy = xpath("//div[contains(@class, 'root')]/h2");
    @FindBy(xpath = "//button[contains(@name, 'data[submit]')]")
    protected WebElement submitButton;

    public CommonTaskPage() {
        loadingPage();
        loadingComponents();
    }

    @SneakyThrows
    public <T extends CommonTaskPage> T checkTaskName(Class<T> clazz, String... taskNamesExpected) {
        wait.until(visibilityOfElementLocated(taskNameBy));
        assertThat(driver.findElement(taskNameBy).getText()).as("Page title is not in expected list!")
                .isIn(Arrays.asList(taskNamesExpected));
        return (T) Arrays.stream(clazz.getConstructors()).findFirst().orElseThrow().newInstance();
    }

    public void submitForm() {
        wait
                .until(elementToBeClickable(submitButton))
                .click();
    }
}
