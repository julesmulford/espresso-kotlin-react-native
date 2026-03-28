package com.saucelabs.tests.tests

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.saucelabs.tests.base.BaseTest
import com.saucelabs.tests.screens.CartScreen
import com.saucelabs.tests.screens.LoginScreen
import com.saucelabs.tests.screens.ProductDetailScreen
import com.saucelabs.tests.screens.ProductListScreen
import com.saucelabs.tests.testdata.TestData
import io.qameta.allure.kotlin.Description
import io.qameta.allure.kotlin.Epic
import io.qameta.allure.kotlin.Feature
import io.qameta.allure.kotlin.Severity
import io.qameta.allure.kotlin.SeverityLevel
import io.qameta.allure.kotlin.Story
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@Epic("Shopping Cart")
@Feature("Cart Management")
class CartTest : BaseTest() {

    @Before
    override fun setUp() {
        super.setUp()
        LoginScreen
            .waitForScreen()
            .loginWith(TestData.Credentials.VALID_USERNAME, TestData.Credentials.VALID_PASSWORD)
        ProductListScreen.waitForScreen()
    }

    @Test
    @Story("View Cart")
    @Severity(SeverityLevel.CRITICAL)
    @Description("After adding a product, the cart should be accessible and contain the item")
    fun cart_afterAddingProduct_shouldContainItem() {
        ProductListScreen
            .tapProductAtPosition(TestData.Products.FIRST_PRODUCT_INDEX)

        ProductDetailScreen
            .waitForScreen()
            .tapAddToCart()

        ProductListScreen
            .waitForScreen()
            .tapCartButton()

        CartScreen
            .waitForScreen()
            .verifyCartVisible()
            .verifyCartHasItem()
    }

    @Test
    @Story("Cart Navigation")
    @Severity(SeverityLevel.NORMAL)
    @Description("The cart checkout button should navigate to the Checkout screen")
    fun cart_tapCheckout_shouldNavigateToCheckout() {
        // Add product first
        ProductListScreen
            .tapProductAtPosition(TestData.Products.FIRST_PRODUCT_INDEX)
        ProductDetailScreen.waitForScreen().tapAddToCart()
        ProductListScreen.waitForScreen().tapCartButton()

        CartScreen
            .waitForScreen()
            .tapCheckout()
    }
}
