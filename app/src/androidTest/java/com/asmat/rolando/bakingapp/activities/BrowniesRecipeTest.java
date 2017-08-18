package com.asmat.rolando.bakingapp.activities;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.assertion.ViewAssertions.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;

import com.asmat.rolando.bakingapp.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class BrowniesRecipeTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testBrowniesRecipe() {
        // Wait a few seconds for network request
        wait(2);

        // Tap on Brownies
        tapListItem(1);

        // Assert Title
        testActionBarTitle("Brownies");

        // Tap Ingredients tab
        onView(allOf(withText("Ingredients"), isDisplayed())).perform(click());

        // Select an ingredient
        onView(allOf(withId(R.id.list), isDisplayed())).perform(actionOnItemAtPosition(0, click()));

        // Tap Steps tab
        onView(allOf(withText("Steps"), isDisplayed())).perform(click());

        // Tap Begin
        onView(allOf(withId(R.id.begin_button), withText("BEGIN"), isDisplayed())).perform(click());

        // Test Steps
        testRecipeStep("Intro");
        testRecipeStep("1", false);
        testRecipeStep("2");
        testRecipeStep("3", false);
        testRecipeStep("4");
        testRecipeStep("5");
        testRecipeStep("6");
        testRecipeStep("7");
        testRecipeStep("8", false);
        testRecipeStep("9");
    }

    private void wait(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void tapListItem(int position) {
        onView(allOf(childAtPosition(
                allOf(withId(android.R.id.list), withContentDescription("recipes list"),
                        withParent(withId(R.id.recipes_list_fragment))),
                position),
                isDisplayed())).perform(click());
    }

    private void testActionBarTitle(String title) {
        onView(allOf(withText(title), isDisplayed())).check(matches(withText(title)));
    }

    private void testRecipeStep(String tabTitle) {
        testRecipeStep(tabTitle, true);
    }

    private void testRecipeStep(String tabTitle, boolean testVideo) {
        // Tap on tab
        wait(1);
        onView(allOf(withText(tabTitle), isDisplayed())).perform(click());
        if(testVideo) {
            // Press Play
            wait(1);
            onView(allOf(withId(R.id.exo_play), isDisplayed())).perform(click());
            // Press Pause
            wait(1);
            onView(allOf(withId(R.id.exo_pause), isDisplayed())).perform(click());
        }
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
