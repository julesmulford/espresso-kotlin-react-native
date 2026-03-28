package com.saucelabs.tests.tests

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.saucelabs.tests.base.BaseTest
import com.saucelabs.tests.screens.LoginScreen
import com.saucelabs.tests.screens.ProductListScreen
import com.saucelabs.tests.testdata.TestData
import io.qameta.allure.kotlin.AllureId
import io.qameta.allure.kotlin.Description
import io.qameta.allure.kotlin.Epic
import io.qameta.allure.kotlin.Feature
import io.qameta.allure.kotlin.Severity
import io.qameta.allure.kotlin.SeverityLevel
import io.qameta.allure.kotlin.Story
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@Epic("Authentication")
@Feature("Login")
class LoginTest : BaseTest() {

    @Test
    @Story("Valid Login")
    @Severity(SeverityLevel.CRITICAL)
    @Description("User with valid credentials should be navigated to the Product List screen")
    fun login_withValidCredentials_shouldNavigateToProductList() {
        LoginScreen
            .waitForScreen()
            .loginWith(TestData.Credentials.VALID_USERNAME, TestData.Credentials.VALID_PASSWORD)

        ProductListScreen
            .waitForScreen()
            .verifyProductListVisible()
    }

    @Test
    @Story("Invalid Login")
    @Severity(SeverityLevel.CRITICAL)
    @Description("User with invalid credentials should see an error message")
    fun login_withInvalidCredentials_shouldShowError() {
        LoginScreen
            .waitForScreen()
            .loginWith(TestData.Credentials.INVALID_USERNAME, TestData.Credentials.INVALID_PASSWORD)
            .verifyErrorMessageDisplayed()
    }

    @Test
    @Story("Empty Credentials")
    @Severity(SeverityLevel.NORMAL)
    @Description("Attempting login with empty fields should show a validation error")
    fun login_withEmptyCredentials_shouldShowValidationError() {
        LoginScreen
            .waitForScreen()
            .loginWith(TestData.Credentials.EMPTY, TestData.Credentials.EMPTY)
            .verifyErrorMessageDisplayed()
    }

    @Test
    @Story("Login Screen Visible")
    @Severity(SeverityLevel.NORMAL)
    @Description("The login screen should be visible on app launch")
    fun loginScreen_onAppLaunch_shouldBeVisible() {
        LoginScreen
            .waitForScreen()
            .verifyLoginScreenVisible()
    }
}
