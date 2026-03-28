package com.saucelabs.tests.screens

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.saucelabs.tests.utils.WaitUtils
import io.qameta.allure.kotlin.Step

/**
 * Screen Object for the Product Detail screen.
 */
object ProductDetailScreen {

    private const val PRODUCT_DETAIL_ID = "test-PRODUCT"
    private const val ADD_TO_CART_BUTTON_ID = "test-ADD TO CART"
    private const val PRODUCT_TITLE_ID = "test-TITLE"
    private const val PRODUCT_PRICE_ID = "test-PRICE"
    private const val BACK_BUTTON_ID = "test-GO BACK"

    @Step("Wait for Product Detail screen to load")
    fun waitForScreen(): ProductDetailScreen {
        WaitUtils.waitForView(withContentDescription(PRODUCT_DETAIL_ID))
        return this
    }

    @Step("Verify Product Detail screen is visible")
    fun verifyProductDetailVisible(): ProductDetailScreen {
        onView(withContentDescription(PRODUCT_DETAIL_ID))
            .check(matches(isDisplayed()))
        return this
    }

    @Step("Tap Add to Cart button")
    fun tapAddToCart(): ProductDetailScreen {
        WaitUtils.waitForView(withContentDescription(ADD_TO_CART_BUTTON_ID))
        onView(withContentDescription(ADD_TO_CART_BUTTON_ID)).perform(click())
        return this
    }

    @Step("Verify Add to Cart button is visible")
    fun verifyAddToCartVisible(): ProductDetailScreen {
        onView(withContentDescription(ADD_TO_CART_BUTTON_ID))
            .check(matches(isDisplayed()))
        return this
    }

    @Step("Verify product title is displayed")
    fun verifyProductTitleDisplayed(): ProductDetailScreen {
        WaitUtils.waitForView(withContentDescription(PRODUCT_TITLE_ID))
        return this
    }

    @Step("Go back to product list")
    fun goBack(): ProductDetailScreen {
        onView(withContentDescription(BACK_BUTTON_ID)).perform(click())
        return this
    }
}
