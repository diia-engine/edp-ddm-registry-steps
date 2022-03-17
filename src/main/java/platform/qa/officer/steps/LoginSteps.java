package platform.qa.officer.steps;

import io.qameta.allure.Step;
import platform.qa.entities.Key;
import platform.qa.entities.User;
import platform.qa.officer.pages.DashboardPage;
import platform.qa.officer.pages.LoginPage;

public class LoginSteps {
    @Step("Авторизація користувача у кабинеті чиновника {user}")
    public DashboardPage loginOfficerPortal(User user) {
        Key userKey = user.getKey();
        return new LoginPage()
                .openAuthWithCesPage()
                .readAndSignKey(userKey.getName(), userKey.getPassword(), userKey.getProvider());
    }
}
