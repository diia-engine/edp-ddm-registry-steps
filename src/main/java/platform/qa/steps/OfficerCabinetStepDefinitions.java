package platform.qa.steps;

import io.cucumber.java.DataTableType;
import io.cucumber.java.ParameterType;
import io.cucumber.java.uk.Дано;
import io.cucumber.java.uk.Коли;
import io.cucumber.java.uk.Та;
import io.cucumber.java.uk.Тоді;
import platform.qa.base.UserProvider;
import platform.qa.entities.FieldData;
import platform.qa.enums.FieldType;
import platform.qa.officer.pages.AvailableServicesPage;
import platform.qa.officer.pages.DashboardPage;
import platform.qa.officer.pages.MyTasksPage;
import platform.qa.officer.pages.SignTaskPage;
import platform.qa.officer.pages.TaskPage;
import platform.qa.officer.panel.OfficerHeaderPanel;
import platform.qa.officer.steps.LoginSteps;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;

public class OfficerCabinetStepDefinitions {

    private UserProvider users = UserProvider.getInstance();

    @DataTableType
    public FieldData fieldEntry(Map<String, String> entry) {
        return new FieldData(entry.get("name"), getFieldType(entry.get("type")), entry.get("value"));
    }

    private FieldType getFieldType(String entry) {
        try {
            return Arrays.stream(FieldType.values())
                    .filter(value -> value.getType().equals(entry))
                    .findFirst()
                    .orElseThrow();
        } catch (NoSuchElementException ex) {
            return FieldType.valueOf(entry);
        }
    }

    @ParameterType(value = "radiobutton|RADIOBUTTON|checkbox|CHECKBOX|input|INPUT|select|SELECT|datetime|DATETIME")
    public FieldType fieldTypeValue(String value) {
        return getFieldType(value.toUpperCase(Locale.ENGLISH));
    }

    @ParameterType(value = "активна|не активна")
    public boolean booleanValue(String value) {
        return value.equals("активна");
    }

    @Дано("користувач {string} успішно відкрив кабінет чиновника")
    public void verifyOfficerOpenDashboardPage(String userName) {
        new LoginSteps()
                .loginOfficerPortal(users.getUserByName(userName));
    }

    @Дано("має доступ до процесу {string}")
    public void verifyProcessAvailable(String processName) {
        new DashboardPage()
                .clickOnAvailableServices()
                .checkProcessByName(processName);
    }

    @Коли("користувач запускає процес {string}")
    public void verifyUserStartProcess(String processName) {
        new AvailableServicesPage()
                .clickOnProcessByName(processName);
    }

    @Тоді("відображається форма {string} із кнопкою \"Далі\" яка {booleanValue}")
    public void verifyDisplayFormName(String formName, boolean isEnabled) {
        new TaskPage()
                .checkTaskName(TaskPage.class, formName)
                .checkSubmitButtonState(isEnabled);
    }

    @Коли("користувач заповнює форму даними$")
    public void userFillFormFieldsWithData(List<FieldData> rows) {
        for (FieldData fieldData : rows) {
            new TaskPage()
                    .setFieldsData(fieldData);
        }
    }

    @Та("натискає кнопку \"Далі\"")
    public void clickButton() {
        new TaskPage()
                .submitForm();
    }

    @Тоді("відображається форма {string} із заповненими даними без можливості редагування")
    public void checkSignForm(String formName) {
        new TaskPage()
                .checkTaskName(TaskPage.class, formName)
                .checkFieldsAreNotEditable();
    }

    @Коли("користувач {string} підписує форму")
    public void signForm(String userName) {
        new SignTaskPage()
                .signTask(users.getUserByName(userName));
    }

    @Тоді("задача {string} виконана")
    public void verifyTaskCompleted(String taskName) {
        new MyTasksPage().checkNotificationMessage(taskName)
                .clickOnProvisionedTasksTab()
                .checkTaskExistsByTaskName(taskName);
    }

    @Та("користувач закриває кабінет чиновника")
    public void logoutOfficerPortal() {
        new OfficerHeaderPanel()
                .clickOnHeaderName()
                .clickOnUserInfoLink()
                .clickLogOutButton();
    }
}
