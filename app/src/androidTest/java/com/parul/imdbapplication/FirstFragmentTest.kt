package com.parul.imdbapplication

import android.view.View
import android.widget.TextView
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.parul.imdbapplication.activity.MainActivity
import com.parul.imdbapplication.fragment.FirstFragment
import com.parul.imdbapplication.fragment.SecondFragment
import org.hamcrest.CoreMatchers
import org.hamcrest.Matcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class FirstFragmentTest {

    //@get : Rule
    //var mActivityRule = FragmentScenario(FirstFragment::class.java)

    @Before
    fun setUp() {
        ActivityScenario.launch(MainActivity::class.java)
            .onActivity{
                it.supportFragmentManager.beginTransaction()
            }

        val address = "C V Raman Nagar, Bangalore"

        val scenario = launchFragmentInContainer<FirstFragment>()

        onView(withId(R.id.tv_address)).perform(setTextInTextView(address))
        //scenario = launchFragmentInContainer()
        //scenario.moveToState(Lifecycle.State.STARTED)
    }

    @Test
    fun testNextBtnClick() {
        val address = "C V Raman Nagar, Bangalore"



       //onView(withId(R.id.tv_address)).perform(setTextInTextView(address))
        onView(withId(R.id.button_first)).perform(click())

        val scenario = launchFragmentInContainer<SecondFragment>()
        //Espresso.closeSoftKeyboard()
    }

    fun setTextInTextView(value: String): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return CoreMatchers.allOf(
                    ViewMatchers.isDisplayed(), ViewMatchers.isAssignableFrom(
                    TextView::class.java))
            }

            override fun perform(uiController: UiController, view: View) {
                (view as TextView).text = value
            }

            override fun getDescription(): String {
                return "replace text"
            }
        }
    }


}