package platform.qa.steps;

import static platform.qa.enums.Context.OFFICER_USER_LOGIN;

import io.cucumber.java.DataTableType;
import io.cucumber.java.ParameterType;
import io.cucumber.java.uk.І;
import io.cucumber.java.uk.Дано;
import io.cucumber.java.uk.Коли;
import io.cucumber.java.uk.Та;
import io.cucumber.java.uk.Тоді;
import platform.qa.base.providers.UserProvider;
import platform.qa.cucumber.TestContext;
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
    private TestContext testContext;

    public OfficerCabinetStepDefinitions(TestContext testContext) {
        this.testContext = testContext;
    }

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

    @Дано("користувач {string} успішно увійшов у кабінет посадової особи")
    public void verifyOfficerOpenDashboardPage(String userName) {
        new LoginSteps()
                .loginOfficerPortal(users.getUserByName(userName));
        testContext.getScenarioContext().setContext(OFFICER_USER_LOGIN, users.getUserByName(userName).getLogin());
    }

    @Дано("бачить доступний процес {string}")
    public void verifyProcessAvailable(String processName) {
        new DashboardPage()
                .clickOnAvailableServices()
                .checkProcessByName(processName);
    }

    @Коли("користувач ініціює процес {string}")
    public void verifyUserStartProcess(String processName) {
        new AvailableServicesPage()
                .clickOnProcessByName(processName);
    }

    @Коли("бачить форму {string} із кнопкою \"Далі\" яка {booleanValue}")
    public void verifyDisplayFormName(String formName, boolean isEnabled) {
        new TaskPage()
                .checkTaskName(TaskPage.class, formName)
                .checkSubmitButtonState(isEnabled);
    }

    @Коли("поле {string} має значення заповнене автоматично")
    public void verifyFieldIsFilledWithData(String fieldName) {
        new TaskPage()
                .checkFieldIsNotEmpty(fieldName);
    }

    @Коли("користувач заповнює форму даними$")
    public void userFillFormFieldsWithData(List<FieldData> rows) {
        for (FieldData fieldData : rows) {
            new TaskPage()
                    .setFieldsData(fieldData);
        }
    }

    @Коли("натискає кнопку \"Додати\" у {string}")
    public void addNewRowToTheTable(String tableName) {
        new TaskPage()
                .addNewRow(tableName);
    }

    @Та("натискає кнопку \"Далі\"")
    public void clickButton() {
        new TaskPage()
                .submitForm();
    }

    @Коли("пересвідчується в правильному відображенні введених даних на формі {string}")
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

    @Коли("підписує їх")
    public void signFormUserFromContext() {
        new SignTaskPage()
                .signTask(users.getUserByName((String) testContext.getScenarioContext().getContext(OFFICER_USER_LOGIN)));
    }

    @Тоді("процес закінчено успішно й задача {string} відображається як виконана у переліку задач")
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

    @І("додає запис до {string} таблиці із даними")
    public void userFillGridFieldsWithData(String gridName, List<FieldData> rows) {
        TaskPage taskPage = new TaskPage();
        taskPage
                .clickAddButton(gridName);
        for (FieldData fieldData : rows) {
            taskPage
                    .setFieldsData(fieldData);
        }
        taskPage
                .clickSaveButton();
    }

}
