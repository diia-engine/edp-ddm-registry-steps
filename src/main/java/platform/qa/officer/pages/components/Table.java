package platform.qa.officer.pages.components;

import platform.qa.base.BasePage;

import java.util.List;
import java.util.stream.Collectors;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class Table extends BasePage {

    @FindBy(xpath = "//tbody//tr[contains(@class, 'MuiTableRow-root')]")
    private List<WebElement> tableRows;

    public Table() {
        loadingPage();
        loadingComponents();
    }

    public List<WebElement> getRowFromTableByTaskName(String taskName) {
        return getTableRows().stream().filter(row->row.getText().contains(taskName)).collect(Collectors.toList());
    }

    private List<WebElement> getTableRows() {
        if (tableRows.isEmpty()) {
            return tableRows;
        } else {
            List<WebElement> tableRows =
                    wait.withMessage("Елементи в таблиці недоступні").until(ExpectedConditions.visibilityOfAllElements(this.tableRows));
            wait = getDefaultWebDriverWait();
            return tableRows;
        }
    }
}
