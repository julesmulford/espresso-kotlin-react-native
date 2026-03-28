package com.saucelabs.tests.tests

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.saucelabs.tests.base.BaseTest
import com.saucelabs.tests.screens.CartScreen
import com.saucelabs.tests.screens.CheckoutScreen
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
@Epic("Checkout")
@Feature("Order Placement")
class CheckoutTest : BaseTest() {

    @Before
    override fun setUp() {
        super.setUp()
        // Full pre-condition: login → add product → go to cart → tap checkout
        LoginScreen
            .waitForScreen()
            .loginWith(TestData.Credentials.VALID_USERNAME, TestData.Credentials.VALID_PASSWORD)

        ProductListScreen.waitForScreen()
            .tapProductAtPosition(TestData.Products.FIRST_PRODUCT_INDEX)

        ProductDetailScreen.waitForScreen().tapAddToCart()
        ProductListScreen.waitForScreen().tapCartButton()
        CartScreen.waitForScreen().tapCheckout()
    }

    @Test
    @Story("Successful Checkout")
    @Severity(SeverityLevel.CRITICAL)
    @Description("User filling checkout form and placing order should see success confirmation")
    fun checkout_withValidDetails_shouldSucceed() {
        CheckoutScreen
            .waitForScreen()
            .fillForm(
                firstName = TestData.Checkout.FIRST_NAME,
                lastName = TestData.Checkout.LAST_NAME,
                zipCode = TestData.Checkout.ZIP_CODE,
            )
            .tapContinue()
            .tapFinish()
            .verifyOrderSuccess()
    }

    @Test
    @Story("Checkout Form Validation")
    @Severity(SeverityLevel.NORMAL)
    @Description("Submitting checkout with empty fields should show a validation error")
    fun checkout_withEmptyForm_shouldShowValidationError() {
        CheckoutScreen
            .waitForScreen()
            .tapContinue()
            .verifyErrorDisplayed()
    }
}
