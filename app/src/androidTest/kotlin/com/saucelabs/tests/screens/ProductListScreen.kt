package com.saucelabs.tests.screens

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.recyclerview.widget.RecyclerView
import com.saucelabs.tests.utils.WaitUtils
import io.qameta.allure.kotlin.Step

/**
 * Screen Object for the Product Listing screen.
 */
object ProductListScreen {

    private const val PRODUCT_LIST_ID = "test-PRODUCTS"
    private const val PRODUCT_ITEM_ID = "test-Item"
    private const val CART_BUTTON_ID = "test-Cart"
    private const val MENU_BUTTON_ID = "test-Menu"

    @Step("Wait for Product List screen to load")
    fun waitForScreen(): ProductListScreen {
        WaitUtils.waitForView(withContentDescription(PRODUCT_LIST_ID))
        return this
    }

    @Step("Verify Product List screen is visible")
    fun verifyProductListVisible(): ProductListScreen {
        onView(withContentDescription(PRODUCT_LIST_ID))
            .check(matches(isDisplayed()))
        return this
    }

    @Step("Tap product at position {position}")
    fun tapProductAtPosition(position: Int): ProductListScreen {
        WaitUtils.waitForView(withContentDescription(PRODUCT_LIST_ID))
        onView(withContentDescription(PRODUCT_LIST_ID))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(position, click())
            )
        return this
    }

    @Step("Tap Cart button")
    fun tapCartButton(): ProductListScreen {
        onView(withContentDescription(CART_BUTTON_ID)).perform(click())
        return this
    }

    @Step("Verify cart button is visible")
    fun verifyCartButtonVisible(): ProductListScreen {
        onView(withContentDescription(CART_BUTTON_ID))
            .check(matches(isDisplayed()))
        return this
    }
}
