package com.saucelabs.tests.utils

import android.os.Environment
import android.util.Log
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * JUnit 4 Rule that captures a screenshot on test failure.
 * Screenshots are saved to the device's Pictures/TestScreenshots directory
 * and can be retrieved via: adb pull /sdcard/Pictures/TestScreenshots/
 */
class ScreenshotRule : TestWatcher() {

    companion object {
        private const val TAG = "ScreenshotRule"
        private const val SCREENSHOT_DIR = "TestScreenshots"
    }

    override fun failed(e: Throwable, description: Description) {
        captureScreenshot(description.methodName ?: "unknown_test")
    }

    fun captureScreenshot(testName: String): String? {
        return try {
            val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
            val safeName = testName.replace(Regex("[^a-zA-Z0-9_]"), "_")
            val fileName = "${safeName}_$timestamp.png"

            val screenshotDir = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                SCREENSHOT_DIR
            )
            if (!screenshotDir.exists()) screenshotDir.mkdirs()

            val file = File(screenshotDir, fileName)
            val instrumentation = InstrumentationRegistry.getInstrumentation()
            val uiAutomation = instrumentation.uiAutomation
            val bitmap = uiAutomation.takeScreenshot()

            if (bitmap != null) {
                FileOutputStream(file).use { out ->
                    bitmap.compress(android.graphics.Bitmap.CompressFormat.PNG, 100, out)
                }
                Log.i(TAG, "Screenshot saved: ${file.absolutePath}")
                file.absolutePath
            } else {
                Log.w(TAG, "Screenshot bitmap was null for test: $testName")
                null
            }
        } catch (ex: Exception) {
            Log.e(TAG, "Failed to capture screenshot for $testName: ${ex.message}")
            null
        }
    }
}
