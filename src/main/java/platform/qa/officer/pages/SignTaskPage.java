package platform.qa.officer.pages;

import io.qameta.allure.Step;
import platform.qa.entities.Key;
import platform.qa.entities.User;

public class SignTaskPage extends CommonTaskPage {

    private final CesWidgetComponent cesWidgetComponent = new CesWidgetComponent();

    public SignTaskPage() {
        loadingPage();
        loadingComponents();
    }

    @Step("Підписати задачу: {taskName} від користувача: {user}")
    public void signTask(User user) {
        Key key = user.getKey();
        cesWidgetComponent.checkReadKeyFormFields().uploadAndReadKey(key.getName(), key.getPassword(), key.getProvider());
        cesWidgetComponent.signKey();
    }
}
