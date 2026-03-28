package com.saucelabs.tests.screens

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.saucelabs.tests.utils.WaitUtils
import io.qameta.allure.kotlin.Step

/**
 * Screen Object for the Cart screen.
 */
object CartScreen {

    private const val CART_CONTENT_ID = "test-Cart Content"
    private const val CHECKOUT_BUTTON_ID = "test-CHECKOUT"
    private const val CART_ITEM_ID = "test-Item"
    private const val CONTINUE_SHOPPING_ID = "test-CONTINUE SHOPPING"
    private const val REMOVE_ITEM_ID = "test-Delete Item"

    @Step("Wait for Cart screen to load")
    fun waitForScreen(): CartScreen {
        WaitUtils.waitForView(withContentDescription(CART_CONTENT_ID))
        return this
    }

    @Step("Verify Cart screen is visible")
    fun verifyCartVisible(): CartScreen {
        onView(withContentDescription(CART_CONTENT_ID))
            .check(matches(isDisplayed()))
        return this
    }

    @Step("Verify cart contains item")
    fun verifyCartHasItem(): CartScreen {
        WaitUtils.waitForView(withContentDescription(CART_ITEM_ID))
        onView(withContentDescription(CART_ITEM_ID))
            .check(matches(isDisplayed()))
        return this
    }

    @Step("Tap Checkout button")
    fun tapCheckout(): CartScreen {
        WaitUtils.waitForView(withContentDescription(CHECKOUT_BUTTON_ID))
        onView(withContentDescription(CHECKOUT_BUTTON_ID)).perform(click())
        return this
    }

    @Step("Tap Continue Shopping")
    fun tapContinueShopping(): CartScreen {
        onView(withContentDescription(CONTINUE_SHOPPING_ID)).perform(click())
        return this
    }
}
