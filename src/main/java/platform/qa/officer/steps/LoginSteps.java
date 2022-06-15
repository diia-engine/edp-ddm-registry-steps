package platform.qa.officer.steps;

import platform.qa.entities.Key;
import platform.qa.entities.User;
import platform.qa.officer.pages.DashboardPage;
import platform.qa.officer.pages.LoginPage;

public class LoginSteps {
    public DashboardPage loginOfficerPortal(User user) {
        Key userKey = user.getKey();
        return new LoginPage()
                .openAuthWithCesPage()
                .readAndSignKey(userKey.getName(), userKey.getPassword(), userKey.getProvider());
    }
}
