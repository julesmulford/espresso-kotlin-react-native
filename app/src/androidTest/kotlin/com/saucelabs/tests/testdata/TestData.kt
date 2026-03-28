package com.saucelabs.tests.testdata

object TestData {

    object Credentials {
        const val VALID_USERNAME = "bob@example.com"
        const val VALID_PASSWORD = "10203040"
        const val INVALID_USERNAME = "invalid@example.com"
        const val INVALID_PASSWORD = "wrongpassword"
        const val EMPTY = ""
    }

    object Products {
        const val FIRST_PRODUCT_INDEX = 0
        const val PRODUCT_NAME_PARTIAL = "Sauce"
        const val EXPECTED_PRODUCT_COUNT_MIN = 1
    }

    object Cart {
        const val DEFAULT_QUANTITY = 1
        const val INCREASED_QUANTITY = 2
    }

    object Checkout {
        const val FIRST_NAME = "John"
        const val LAST_NAME = "Doe"
        const val ZIP_CODE = "12345"
    }
}
