package platform.qa.officer.pages;

import platform.qa.officer.pages.components.CesWidget;

public class AuthWithCesPage extends OfficerBasePage {

    public AuthWithCesPage() {
        loadingPage();
        loadingComponents();
    }

    public DashboardPage readAndSignKey(String key, String password, String provider) {
        new CesWidget().uploadCustomKey(key, password, provider).signKey();
        return new DashboardPage();
    }
}
