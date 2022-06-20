package platform.qa.base;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j;

import java.io.File;
import java.net.URI;
import java.nio.file.Paths;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

@Log4j
public class Screenshot {
    private static final String SCREENSHOTS_PATH = "target/screenshots";

    @SneakyThrows
    public static void takeScreenshot() {
        WebDriver driver = WebDriverProvider.getInstance();
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        URI screenshotPath =
                Paths.get(SCREENSHOTS_PATH, "screenshot_" + System.nanoTime() + ".png").toUri();
        File copy = new File(screenshotPath);
        FileUtils.copyFile(screenshot, copy);
        log.info("Screenshot: " + copy.getAbsolutePath());
    }
}
