package platform.qa.officer.pages;

import static com.codeborne.selenide.Selenide.page;

import io.qameta.allure.Step;
import platform.qa.officer.pages.components.CesWidget;

public class AuthWithCesPage extends OfficerBasePage {

    public AuthWithCesPage() {
        loadingPage();
        loadingComponents();
    }

    @Step("Зчитування ключа користувача у кабінеті чиновника")
    public DashboardPage readAndSignKey(String key, String password, String provider) {
        new CesWidget().uploadCustomKey(key, password, provider).signKey();
        loadingComponents();
        return page(new DashboardPage());
    }
}
