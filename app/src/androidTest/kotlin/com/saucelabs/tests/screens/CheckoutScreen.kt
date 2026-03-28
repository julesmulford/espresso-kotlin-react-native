package com.saucelabs.tests.screens

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.saucelabs.tests.utils.WaitUtils
import io.qameta.allure.kotlin.Step

/**
 * Screen Object for the Checkout screen.
 */
object CheckoutScreen {

    private const val CHECKOUT_SCREEN_ID = "test-Checkout"
    private const val FIRST_NAME_ID = "test-First Name"
    private const val LAST_NAME_ID = "test-Last Name"
    private const val ZIP_CODE_ID = "test-Zip/Postal Code"
    private const val CONTINUE_BUTTON_ID = "test-CONTINUE"
    private const val FINISH_BUTTON_ID = "test-PLACE ORDER"
    private const val SUCCESS_MESSAGE_ID = "test-CHECKOUT_COMPLETE"
    private const val ERROR_MESSAGE_ID = "test-Error Message"

    @Step("Wait for Checkout screen to load")
    fun waitForScreen(): CheckoutScreen {
        WaitUtils.waitForView(withContentDescription(CHECKOUT_SCREEN_ID))
        return this
    }

    @Step("Enter first name: {firstName}")
    fun enterFirstName(firstName: String): CheckoutScreen {
        onView(withContentDescription(FIRST_NAME_ID))
            .perform(clearText(), typeText(firstName), closeSoftKeyboard())
        return this
    }

    @Step("Enter last name: {lastName}")
    fun enterLastName(lastName: String): CheckoutScreen {
        onView(withContentDescription(LAST_NAME_ID))
            .perform(clearText(), typeText(lastName), closeSoftKeyboard())
        return this
    }

    @Step("Enter zip code: {zipCode}")
    fun enterZipCode(zipCode: String): CheckoutScreen {
        onView(withContentDescription(ZIP_CODE_ID))
            .perform(clearText(), typeText(zipCode), closeSoftKeyboard())
        return this
    }

    @Step("Tap Continue button")
    fun tapContinue(): CheckoutScreen {
        onView(withContentDescription(CONTINUE_BUTTON_ID)).perform(click())
        return this
    }

    @Step("Tap Place Order / Finish button")
    fun tapFinish(): CheckoutScreen {
        WaitUtils.waitForView(withContentDescription(FINISH_BUTTON_ID))
        onView(withContentDescription(FINISH_BUTTON_ID)).perform(click())
        return this
    }

    @Step("Verify order success message is displayed")
    fun verifyOrderSuccess(): CheckoutScreen {
        WaitUtils.waitForView(withContentDescription(SUCCESS_MESSAGE_ID))
        onView(withContentDescription(SUCCESS_MESSAGE_ID))
            .check(matches(isDisplayed()))
        return this
    }

    @Step("Fill checkout form")
    fun fillForm(firstName: String, lastName: String, zipCode: String): CheckoutScreen {
        enterFirstName(firstName)
        enterLastName(lastName)
        enterZipCode(zipCode)
        return this
    }

    @Step("Verify error message is displayed")
    fun verifyErrorDisplayed(): CheckoutScreen {
        WaitUtils.waitForView(withContentDescription(ERROR_MESSAGE_ID))
        return this
    }
}
