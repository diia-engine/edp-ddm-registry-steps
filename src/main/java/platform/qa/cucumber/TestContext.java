package platform.qa.cucumber;

import lombok.Getter;

@Getter
public class TestContext {
    private ScenarioContext scenarioContext;

    public TestContext() {
        this.scenarioContext = new ScenarioContext();
    }
}
