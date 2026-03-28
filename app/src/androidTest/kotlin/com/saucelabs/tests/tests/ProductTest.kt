package com.saucelabs.tests.tests

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.saucelabs.tests.base.BaseTest
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
@Epic("Product Catalog")
@Feature("Product Listing and Detail")
class ProductTest : BaseTest() {

    @Before
    override fun setUp() {
        super.setUp()
        // Pre-condition: user is logged in
        LoginScreen
            .waitForScreen()
            .loginWith(TestData.Credentials.VALID_USERNAME, TestData.Credentials.VALID_PASSWORD)
        ProductListScreen.waitForScreen()
    }

    @Test
    @Story("Product List Visible")
    @Severity(SeverityLevel.CRITICAL)
    @Description("After login, the product list should be visible with products")
    fun productList_afterLogin_shouldBeVisible() {
        ProductListScreen
            .verifyProductListVisible()
            .verifyCartButtonVisible()
    }

    @Test
    @Story("Product Detail")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Tapping a product should open the Product Detail screen")
    fun product_tapFirstItem_shouldOpenDetailScreen() {
        ProductListScreen
            .tapProductAtPosition(TestData.Products.FIRST_PRODUCT_INDEX)

        ProductDetailScreen
            .waitForScreen()
            .verifyProductDetailVisible()
            .verifyAddToCartVisible()
            .verifyProductTitleDisplayed()
    }

    @Test
    @Story("Add to Cart from Detail")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Tapping Add to Cart on the detail screen should add the product to the cart")
    fun product_addToCart_shouldSucceed() {
        ProductListScreen
            .tapProductAtPosition(TestData.Products.FIRST_PRODUCT_INDEX)

        ProductDetailScreen
            .waitForScreen()
            .tapAddToCart()
    }
}
