package com.saucelabs.tests.screens

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.saucelabs.tests.utils.WaitUtils
import io.qameta.allure.kotlin.Step

/**
 * Screen Object representing the Login screen of the React Native demo app.
 *
 * Resource IDs correspond to testID props set in the React Native source.
 * If the actual app uses different resource IDs, update these constants.
 */
object LoginScreen {

    // React Native testID values translated to Android resource IDs
    private const val USERNAME_FIELD_ID = "test-Username"
    private const val PASSWORD_FIELD_ID = "test-Password"
    private const val LOGIN_BUTTON_ID = "test-LOGIN"
    private const val ERROR_MESSAGE_ID = "test-Error"
    private const val LOGIN_CONTAINER_ID = "test-Login"

    @Step("Wait for Login screen to load")
    fun waitForScreen(): LoginScreen {
        WaitUtils.waitForView(withContentDescription(LOGIN_CONTAINER_ID))
        return this
    }

    @Step("Enter username: {username}")
    fun enterUsername(username: String): LoginScreen {
        onView(withContentDescription(USERNAME_FIELD_ID))
            .perform(clearText(), typeText(username), closeSoftKeyboard())
        return this
    }

    @Step("Enter password")
    fun enterPassword(password: String): LoginScreen {
        onView(withContentDescription(PASSWORD_FIELD_ID))
            .perform(clearText(), typeText(password), closeSoftKeyboard())
        return this
    }

    @Step("Tap Login button")
    fun tapLoginButton(): LoginScreen {
        onView(withContentDescription(LOGIN_BUTTON_ID)).perform(click())
        return this
    }

    @Step("Login with credentials")
    fun loginWith(username: String, password: String): LoginScreen {
        enterUsername(username)
        enterPassword(password)
        tapLoginButton()
        return this
    }

    @Step("Verify error message is displayed")
    fun verifyErrorMessageDisplayed(): LoginScreen {
        WaitUtils.waitForView(withContentDescription(ERROR_MESSAGE_ID))
        onView(withContentDescription(ERROR_MESSAGE_ID))
            .check(matches(isDisplayed()))
        return this
    }

    @Step("Verify error message contains text")
    fun verifyErrorMessage(expectedText: String): LoginScreen {
        WaitUtils.waitForView(withContentDescription(ERROR_MESSAGE_ID))
        onView(withContentDescription(ERROR_MESSAGE_ID))
            .check(matches(withText(org.hamcrest.Matchers.containsString(expectedText))))
        return this
    }

    @Step("Verify Login screen is visible")
    fun verifyLoginScreenVisible(): LoginScreen {
        onView(withContentDescription(LOGIN_BUTTON_ID))
            .check(matches(isDisplayed()))
        return this
    }
}
