package platform.qa.officer.pages;

import io.qameta.allure.Step;
import platform.qa.entities.Key;
import platform.qa.entities.User;
import platform.qa.officer.pages.components.CesWidget;

public class SignTaskPage extends CommonTaskPage {

    private final CesWidget cesWidget = new CesWidget();

    public SignTaskPage() {
        loadingPage();
        loadingComponents();
    }

    @Step("Підписати задачу: {taskName} від користувача: {user}")
    public void signTask(User user) {
        Key key = user.getKey();
        cesWidget.checkReadKeyFormFields().uploadAndReadKey(key.getName(), key.getPassword(), key.getProvider());
        cesWidget.signKey();
    }
}
