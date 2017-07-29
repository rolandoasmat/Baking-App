package com.asmat.rolando.bakingapp

import android.support.test.espresso.Espresso.*
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.espresso.Espresso.onData
import org.hamcrest.Matchers.anything
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.asmat.rolando.bakingapp.activities.MainActivity

import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith



/**
 * Created by rolandoasmat on 7/29/17.
 */


@RunWith(AndroidJUnit4::class)
public class MainActivityPickARecipeTest {

    @get:Rule
    public val mActivityTestRule = ActivityTestRule<MainActivity>(MainActivity::class.java)

    @Test
    public fun testRecipesList() {
        Thread.sleep(5000)

        onData(anything()).inAdapterView(withContentDescription("recipes list")).atPosition(0).perform(click())
        pressBack()

        onData(anything()).inAdapterView(withContentDescription("recipes list")).atPosition(1).perform(click())
        pressBack()

        onData(anything()).inAdapterView(withContentDescription("recipes list")).atPosition(2).perform(click())
        pressBack()

        onData(anything()).inAdapterView(withContentDescription("recipes list")).atPosition(3).perform(click())
        pressBack()

    }




}