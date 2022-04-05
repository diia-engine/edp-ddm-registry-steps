package platform.qa.officer.pages;

import static com.codeborne.selenide.Condition.disabled;
import static com.codeborne.selenide.Condition.empty;
import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static java.lang.String.format;
import static org.openqa.selenium.By.xpath;

import io.qameta.allure.Step;
import platform.qa.entities.FieldData;
import platform.qa.officer.pages.components.Select;

import com.codeborne.selenide.CollectionCondition;

public class TaskPage extends CommonTaskPage {

    private final String inputPath = "//label[text()[contains(.,'%s')]]/following-sibling::div/input[@type='text']";
    private final String radioButtonPath = "//span[text()[contains(.,'%s')]]/preceding-sibling::input[@type='radio']";
    private final String checkboxPath = "//span[text()[contains(.,'%s')]]/preceding-sibling::input[@type='checkbox']";
    private final String dateTimePath = "//label[text()[contains(.,'%s')]]/following-sibling::div//input[@type='text']";

    public TaskPage() {
        loadingPage();
        loadingComponents();
    }

    @Step("Перевірка полів задачі на 'read only' властивість")
    public void checkFieldsAreNotEditable() {
        $$(xpath("//input[contains(@name, 'data')]"))
                .shouldHave(CollectionCondition.sizeGreaterThan(0))
                .shouldBe(CollectionCondition
                        .allMatch("Check all fields are read only",
                                element -> element.getAttribute("disabled").equals("true")));
    }

    @Step("Введення даних у текстове поле {filedName}: {fieldData}")
    public void fillInputFieldWithData(String fieldName, String fieldData) {
        $(xpath(format(inputPath, fieldName)))
                .shouldBe(visible)
                .shouldBe(enabled)
                .setValue(fieldData);
    }


    @Step ("Перевірка кнопки Далі")
    public void checkSubmitButtonState(boolean isEnabled){
        if (isEnabled) {
            $(xpath("//button[@type=\"submit\"]")).shouldBe(enabled);
        }else {
            $(xpath("//button[@type=\"submit\"]")).shouldBe(disabled);
        }

    }

    @Step("Введення даних у поле {filedName}: {fieldData}")
    public void selectDataFromDateTime(String fieldName, String fieldData) {
        $(xpath(format(dateTimePath, fieldName)))
                .shouldBe(visible)
                .setValue(fieldData)
                .pressTab();
    }

    @Step("Вибір радіобатона {filedName}: {fieldData}")
    public void checkRadioButton(String fieldData) {
        $(xpath(format(radioButtonPath, fieldData)))
                .shouldNotBe(empty)
                .click();
    }

    @Step("Вибір чекбокса {fieldData}")
    public void checkCheckBox(String fieldName, String fieldData) {
        if (fieldData.equalsIgnoreCase("так"))
            $(xpath(format(checkboxPath, fieldName))).click();
    }

    @Step("Введення даних у текстове поле {filedName}: {fieldData}")
    public void setFieldsData(FieldData fieldData) {
        switch (fieldData.getType()) {
                case RADIOBUTTON:
                    checkRadioButton(fieldData.getValue());
                    break;
                case CHECKBOX:
                    checkCheckBox(fieldData.getName(), fieldData.getValue());
                    break;
                case INPUT:
                    fillInputFieldWithData(fieldData.getName(), fieldData.getValue());
                    break;
                case SELECT:
                    new Select().selectItemFromDropDown(fieldData.getName(), fieldData.getValue());
                    break;
                case DATETIME:
                    selectDataFromDateTime(fieldData.getName(), fieldData.getValue());
                    break;
            }
    }
}
