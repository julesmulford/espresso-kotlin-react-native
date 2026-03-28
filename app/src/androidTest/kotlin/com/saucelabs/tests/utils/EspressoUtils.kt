package com.saucelabs.tests.utils

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf

object EspressoUtils {

    /**
     * Returns a ViewAction that scrolls to an item in a RecyclerView
     * and clicks it at the given position.
     */
    fun clickRecyclerItem(recyclerViewId: Int, position: Int): ViewInteraction {
        return onView(withId(recyclerViewId))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(position, click()))
    }

    /**
     * Returns a matcher for a child view of a parent with the given id.
     */
    fun childOf(parentId: Int, childId: Int): Matcher<View> {
        return allOf(withId(childId), isDescendantOfA(withId(parentId)))
    }

    /**
     * ViewAction that waits for a view to be enabled.
     */
    fun waitForEnabled(timeoutMs: Long = 5000L): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> = isDisplayed()
            override fun getDescription(): String = "wait for view to be enabled"
            override fun perform(uiController: UiController, view: View) {
                uiController.loopMainThreadUntilIdle()
                val deadline = System.currentTimeMillis() + timeoutMs
                while (System.currentTimeMillis() < deadline && !view.isEnabled) {
                    uiController.loopMainThreadForAtLeast(100)
                }
                if (!view.isEnabled) {
                    throw RuntimeException("View was not enabled within ${timeoutMs}ms")
                }
            }
        }
    }

    /**
     * Matcher for a view that contains the given text (case-insensitive substring).
     */
    fun withTextContaining(substring: String): Matcher<View> {
        return withText(org.hamcrest.Matchers.containsStringIgnoringCase(substring))
    }
}
