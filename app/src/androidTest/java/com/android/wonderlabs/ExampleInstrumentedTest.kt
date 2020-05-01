package com.android.wonderlabs

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.test.espresso.Espresso
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.internal.runner.junit4.statement.UiThreadStatement
import androidx.test.rule.ActivityTestRule
import cchcc.learn.amu.util.ViewAssertionsEx
import com.android.wonderlabs.ui.MainActivity
import com.android.wonderlabs.ui.MainFragment
import com.android.wonderlabs.ui.MainViewModel
import com.android.wonderlabs.ui.adapter.MainAdapter
import org.hamcrest.Matcher
import org.junit.Assert

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.android.wonderlabs", appContext.packageName)
    }

    lateinit var fragment: MainFragment
    lateinit var viewModel: MainViewModel

    @get:Rule
    val rule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun observeData() {
        UiThreadStatement.runOnUiThread {
            viewModel = MainViewModel()
            fragment = MainFragment.newInstance { viewModel }
            rule.activity.startFragment(fragment)
        }
    }

    @Test
    fun testService() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext

        UiThreadStatement.runOnUiThread {
            viewModel.data.observe(fragment, Observer {})
            viewModel.getData(appContext)
        }

        // when
        Espresso.onView(withId(R.id.recyclerView)).perform()

        assertNotNull(viewModel.data)
        assertNotEquals(0, viewModel.data.value!!.size)
    }

    @Test
    fun testRV() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext

        UiThreadStatement.runOnUiThread {
            viewModel.data.observe(fragment, Observer {})
            viewModel.getData(appContext)
        }

        // when
        Espresso.onView(withId(R.id.recyclerView))
            .check(ViewAssertionsEx.hasItemCountOfRecyclerView(viewModel.data.value!!.size))
    }

    @Test
    fun testDetails() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext

        UiThreadStatement.runOnUiThread {
            viewModel.getData(appContext)
        }

        // when
        Espresso.onView(withId(R.id.recyclerView)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.recyclerViewDetails)).check(ViewAssertionsEx.isVisible())
    }

    @Test
    fun testDecrease() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext

        UiThreadStatement.runOnUiThread {
            viewModel.decreasePosition.observe(fragment, Observer {})
            viewModel.getData(appContext)
        }

        // when
        Espresso.onView(withId(R.id.recyclerView)).perform()

        // then
        viewModel.decreasePosition.value!!.forEach {
            Espresso.onView(withId(R.id.recyclerView))
                .perform(
                    RecyclerViewActions.actionOnItemAtPosition<MainAdapter.MainViewHolder>(it
                        , object : ViewAction {
                            override fun getDescription(): String = "AAA"

                            override fun getConstraints(): Matcher<View> = isDisplayed()

                            override fun perform(uiController: UiController, view: View) {
                                val isVisible = view.findViewById<AppCompatImageView>(R.id.image).isVisible
                                assertTrue(isVisible)
                            }
                        })
                )
        }
    }
}
