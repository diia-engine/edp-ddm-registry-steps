/*
 * Copyright 2022 EPAM Systems.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package platform.qa.officer.pages;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;

import lombok.extern.log4j.Log4j2;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

@Log4j2
public class LoginPage extends OfficerBasePage {

    @FindBy(xpath = "//button[@data-xpath='loginButton']")
    WebElement authButton;

    public AuthWithCesPage openAuthWithCesPage() {
        return openPage()
                .checkThatAuthorizationButtonIsActive()
                .clickAuthorizationButton();
    }

    public LoginPage openPage() {
        openPage(baseUrl);
        wait
                .withMessage("Поточний URL не збігається з очікуваним")
                .until(ExpectedConditions.urlToBe(getExpectedLoginUrl()));
        wait = getDefaultWebDriverWait();
        return this;
    }

    public AuthWithCesPage clickAuthorizationButton() {
        wait
                .until(visibilityOf(authButton))
                .click();
        return new AuthWithCesPage();
    }

    public LoginPage checkThatAuthorizationButtonIsActive() {
        wait.until(elementToBeClickable(authButton));
        return this;
    }

    private String getExpectedLoginUrl() {
        String loginUrl = baseUrl;
        if (baseUrl.lastIndexOf("/") < 0) loginUrl = baseUrl + "/officer/login";
        if (baseUrl.endsWith("/") && !baseUrl.endsWith("officer/")) loginUrl = baseUrl + "officer/login";
        if (baseUrl.endsWith("officer")) loginUrl = baseUrl + "/login";
        if (baseUrl.endsWith("officer/")) loginUrl = baseUrl + "login";
        log.info("Base url = " + baseUrl);
        log.info("Expected login url = " + loginUrl);
        return loginUrl;
    }
}
