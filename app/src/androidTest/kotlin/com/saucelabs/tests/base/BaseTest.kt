package com.saucelabs.tests.base

import android.util.Log
import androidx.test.core.app.ActivityScenario
import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth.assertThat
import com.saucelabs.mydemoapp.rn.MainActivity
import com.saucelabs.tests.utils.ScreenshotRule
import com.saucelabs.tests.utils.WaitUtils
import io.qameta.allure.kotlin.Allure
import org.junit.After
import org.junit.Before
import org.junit.Rule
import java.util.UUID

/**
 * Base class for all Espresso instrumented tests.
 *
 * Responsibilities:
 * - Launch the app Activity under test using ActivityScenario
 * - Register ScreenshotRule for automatic screenshot-on-failure
 * - Provide structured Allure step logging
 * - Handle React Native initial load delay
 * - Tear down cleanly after each test
 */
abstract class BaseTest {

    @get:Rule
    val screenshotRule = ScreenshotRule()

    protected lateinit var scenario: ActivityScenario<MainActivity>

    private val correlationId: String = UUID.randomUUID().toString().substring(0, 8)

    companion object {
        private const val TAG = "BaseTest"
        private const val RN_INITIAL_LOAD_DELAY_MS = 2500L
    }

    @Before
    open fun setUp() {
        Log.i(TAG, "[$correlationId] Starting test: ${javaClass.simpleName}")
        Allure.parameter("CorrelationId", correlationId)
        Allure.parameter("Device", getDeviceInfo())

        scenario = ActivityScenario.launch(MainActivity::class.java)

        // Allow React Native bundle to load before interacting with UI
        WaitUtils.waitForReactNativeLoad(RN_INITIAL_LOAD_DELAY_MS)
    }

    @After
    open fun tearDown() {
        Log.i(TAG, "[$correlationId] Tearing down test: ${javaClass.simpleName}")
        try {
            // Capture a final state screenshot (attached to Allure if test fails via ScreenshotRule)
            screenshotRule.captureScreenshot("teardown_${javaClass.simpleName}_$correlationId")
        } catch (e: Exception) {
            Log.w(TAG, "Failed to capture teardown screenshot: ${e.message}")
        }
        if (::scenario.isInitialized) {
            scenario.close()
        }
    }

    private fun getDeviceInfo(): String {
        return try {
            "${android.os.Build.MANUFACTURER} ${android.os.Build.MODEL} (API ${android.os.Build.VERSION.SDK_INT})"
        } catch (e: Exception) {
            "Unknown Device"
        }
    }
}
