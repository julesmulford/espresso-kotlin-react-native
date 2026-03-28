package com.saucelabs.tests.utils

import android.os.SystemClock
import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import org.hamcrest.Matcher
import android.util.Log

object WaitUtils {

    private const val TAG = "WaitUtils"
    private const val DEFAULT_TIMEOUT_MS = 10000L
    private const val POLL_INTERVAL_MS = 500L

    /**
     * Waits for a view matching the given matcher to be displayed.
     * Polls at POLL_INTERVAL_MS intervals until DEFAULT_TIMEOUT_MS or custom timeout.
     *
     * This is essential for React Native because views may render asynchronously
     * after the JS bundle loads and components mount.
     */
    fun waitForView(
        viewMatcher: Matcher<View>,
        timeoutMs: Long = DEFAULT_TIMEOUT_MS,
    ): ViewInteraction {
        val deadline = SystemClock.elapsedRealtime() + timeoutMs
        var lastException: Throwable? = null

        while (SystemClock.elapsedRealtime() < deadline) {
            try {
                val interaction = onView(viewMatcher)
                interaction.check(matches(isDisplayed()))
                return interaction
            } catch (e: Throwable) {
                lastException = e
                Log.d(TAG, "View not yet visible, retrying... (${deadline - SystemClock.elapsedRealtime()}ms remaining)")
                SystemClock.sleep(POLL_INTERVAL_MS)
            }
        }
        throw AssertionError(
            "View matching [$viewMatcher] was not displayed within ${timeoutMs}ms",
            lastException,
        )
    }

    /**
     * Waits for React Native to finish initial rendering.
     * Adds a small delay to allow the JS bundle to load and components to mount.
     * This is a pragmatic approach for cases where IdlingResource is not available.
     */
    fun waitForReactNativeLoad(delayMs: Long = 2000L) {
        Log.d(TAG, "Waiting ${delayMs}ms for React Native initial render...")
        SystemClock.sleep(delayMs)
    }

    /**
     * Waits for a condition to become true, polling at intervals.
     */
    fun waitForCondition(
        condition: () -> Boolean,
        timeoutMs: Long = DEFAULT_TIMEOUT_MS,
        description: String = "condition",
    ) {
        val deadline = SystemClock.elapsedRealtime() + timeoutMs
        while (SystemClock.elapsedRealtime() < deadline) {
            if (condition()) return
            SystemClock.sleep(POLL_INTERVAL_MS)
        }
        throw AssertionError("Condition '$description' was not met within ${timeoutMs}ms")
    }
}
