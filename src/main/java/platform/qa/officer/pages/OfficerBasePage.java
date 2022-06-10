package platform.qa.officer.pages;

import platform.qa.base.BasePage;

public abstract class OfficerBasePage extends BasePage {

    protected String baseUrl = registryConfig.getOfficerPortal().getUrl() + "/";
}
