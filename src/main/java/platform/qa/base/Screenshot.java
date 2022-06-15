package platform.qa.base;

import lombok.SneakyThrows;

import java.io.File;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class Screenshot {
    private static final String SCREENSHOTS_NAME_TPL = "screenshots/screenshot_";

    @SneakyThrows
    public static void takeScreenshot() {
        WebDriver driver = WebDriverProvider.getInstance();
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String screenshotName = SCREENSHOTS_NAME_TPL + System.nanoTime();
        File copy = new File(screenshotName + ".png");
        FileUtils.copyFile(screenshot, copy);
    }
}
