package com.asmat.rolando.bakingapp;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;

import com.asmat.rolando.bakingapp.activities.MainActivity;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;


/**
 * Created by rolandoasmat on 8/15/17.
 */

public class MainActivityPickARecipeTest {

    private final ActivityTestRule mActivityTestRule = new ActivityTestRule(MainActivity.class);

    @Rule
    public final ActivityTestRule getMActivityTestRule() {
        return this.mActivityTestRule;
    }

    @Test
    public final void testRecipesList() {
        try {
            // Wait for loading to stop
            Thread.sleep(5000L);
            // Tap on Nutella Pie
            Espresso.onData(Matchers.anything()).inAdapterView(ViewMatchers.withContentDescription("recipes list")).atPosition(Integer.valueOf(0)).perform(new ViewAction[]{ViewActions.click()});
            Espresso.pressBack();
            // Tap on Brownies
            Espresso.onData(Matchers.anything()).inAdapterView(ViewMatchers.withContentDescription("recipes list")).atPosition(Integer.valueOf(1)).perform(new ViewAction[]{ViewActions.click()});
            Espresso.pressBack();
            // Tap on Yellow Cake
            Espresso.onData(Matchers.anything()).inAdapterView(ViewMatchers.withContentDescription("recipes list")).atPosition(Integer.valueOf(2)).perform(new ViewAction[]{ViewActions.click()});
            Espresso.pressBack();
            // Tap on Cheesecake
            Espresso.onData(Matchers.anything()).inAdapterView(ViewMatchers.withContentDescription("recipes list")).atPosition(Integer.valueOf(3)).perform(new ViewAction[]{ViewActions.click()});
            Espresso.pressBack();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
