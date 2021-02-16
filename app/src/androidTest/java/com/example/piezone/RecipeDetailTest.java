package com.example.piezone;

import android.content.Context;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static org.hamcrest.Matchers.allOf;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;

import com.example.piezone.model.Recipe;
import com.example.piezone.ui.MainActivity;
import com.example.piezone.ui.StepDetailActivity;
import com.example.piezone.widget.WidgetPrefs;

@RunWith(AndroidJUnit4.class)
public class RecipeDetailTest {
    @Rule
    public IntentsTestRule<MainActivity> intentsTestRule =
            new IntentsTestRule<>(MainActivity.class);

    /**
     * Click a step in the step list and check if the detail is opened
     */
    @Test
    public void clickRecipeStep_openStepDetail() {
        onView(withId(R.id.rv_recipes)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        onView(withId(R.id.rv_steps)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));


        //If it is not a tablet check if the TabLayout is displayed and it has an intent
        boolean isTablet = intentsTestRule.getActivity().getResources().getBoolean(R.bool.two_pane);
        if (isTablet) {
            onView(withId(R.id.tv_step_description)).check(matches(isDisplayed()));
        } else {
            onView(withId(R.id.layout_tab)).check(matches(isDisplayed()));
            onView(allOf(withId(R.id.tv_step_description), isDisplayed())).check(matches(isCompletelyDisplayed()));
            intended(hasComponent(StepDetailActivity.class.getName()));
            intended(hasExtraWithKey(StepDetailActivity.RECIPE_KEY));
            intended(hasExtraWithKey(StepDetailActivity.STEP_KEY));
        }

    }

    /**
     * Clear the preferences and test if the AddToWidget button adds a recipe
     */
    @Test
    public void clickAddToWidget_hasRecipe() {
        //Clear preference and check if it is cleared
        intentsTestRule.getActivity().getSharedPreferences(WidgetPrefs.PREFS_KEY, Context.MODE_PRIVATE)
                .edit()
                .clear()
                .commit();
        Recipe recipe = WidgetPrefs.loadRecipe(intentsTestRule.getActivity());
        assertNull(recipe);

        onView(withId(R.id.rv_recipes)).perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));
        openActionBarOverflowOrOptionsMenu(intentsTestRule.getActivity());
        onView(withText(R.string.add_to_widget))
                .check(matches(isDisplayed()))
                .perform(click());

        //Get and check the saved recipe
        Recipe newRecipe = WidgetPrefs.loadRecipe(intentsTestRule.getActivity());
        assertNotNull(newRecipe);
    }


}
