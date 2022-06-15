package platform.qa.officer.panel;

import static org.openqa.selenium.support.ui.ExpectedConditions.attributeToBeNotEmpty;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;

import platform.qa.officer.pages.AvailableServicesPage;
import platform.qa.officer.pages.DashboardPage;
import platform.qa.officer.pages.MyServicesPage;
import platform.qa.officer.pages.MyTasksPage;
import platform.qa.officer.pages.OfficerBasePage;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class OfficerHeaderPanel extends OfficerBasePage {

    @FindBy(xpath = "//header//div//a[contains(@href,'home')]")
    private WebElement headerName;
    @FindBy(xpath = "//div[@data-xpath='headerLinks']//a[contains(@href,'home')]")
    private WebElement mainLink;
    @FindBy(xpath = "//a[contains(@href,'process-list')]")
    private WebElement availableServicesLink;
    @FindBy(xpath = "//div[@data-xpath='headerLinks']//a[contains(@href,'process-instance-list')]")
    private WebElement myServicesLink;
    @FindBy(xpath = "//div[@data-xpath='headerLinks']//a[contains(@href,'user-tasks-list')]")
    private WebElement myTasksLink;
    @FindBy(xpath = "//div[@data-xpath='headerUserInfo']//span//span")
    private WebElement userInfoLink;

    public OfficerHeaderPanel() {
        loadingPage();
        loadingComponents();
    }

    public OfficerHeaderPanel checkHeaderHasLink() {
        wait.until(visibilityOf(headerName));
        wait.until(attributeToBeNotEmpty(headerName, "href"));
        return this;
    }

    public OfficerHeaderPanel clickOnHeaderName() {
        String href = wait.until(visibilityOf(headerName)).getAttribute("href");
        driver.get(href);
        return this;
    }

    public DashboardPage clickOnMainLink() {
        wait.until(visibilityOf(mainLink)).click();
        return new DashboardPage();
    }

    public MyServicesPage clickOnMyServicesLink() {
        wait.until(visibilityOf(myServicesLink)).click();
        return new MyServicesPage();
    }

    public MyTasksPage clickOnMyTasksLink() {
        wait.until(visibilityOf(myTasksLink)).click();
        return new MyTasksPage();
    }

    public AvailableServicesPage clickOnAvailableServicesLink() {
        wait.until(visibilityOf(availableServicesLink)).click();
        return new AvailableServicesPage();
    }

    public UserInfoPopUp clickOnUserInfoLink() {
        wait.until(visibilityOf(userInfoLink)).click();
        return new UserInfoPopUp();
    }
}
