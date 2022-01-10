package mdtu.platform.qa.cucumber;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mdtu.platform.qa.base.Config;
import mdtu.platform.qa.db.DataSourceInitializer;
import platform.qa.config.ConfigProvider;

import javax.sql.DataSource;

@Getter
public class TestContext {
    private ScenarioContext scenarioContext;

    public TestContext() {
        this.scenarioContext = new ScenarioContext();
    }
}
