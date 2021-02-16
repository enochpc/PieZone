package com.example.piezone;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;

import com.example.piezone.ui.MainActivity;
import com.example.piezone.ui.RecipeDetailActivity;

@RunWith(AndroidJUnit4.class)
public class RecipeListTest {

    @Rule
    public IntentsTestRule<MainActivity> intentsTestRule =
            new IntentsTestRule<>(MainActivity.class);

    /**
     * Click a Recipe in the Mainactivity and check if the DetailActivity is opened properly
     */
    @Test
    public void clickRecipeItem_openRecipeDetail(){
        onView(withId(R.id.rv_recipes)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        //Check if the ingredients and the steps are displayed
        onView(withId(R.id.tv_ingredients)).check(matches(isDisplayed()));

        onView(withId(R.id.rv_steps)).check(matches(isDisplayed()));
    }

    /**
     * Click the Recipe and check if it sends a recipe intent extra
     */
    @Test
    public void clickRecipeItem_hasRecipeIntent(){
        onView(withId(R.id.rv_recipes)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        intended(hasComponent(RecipeDetailActivity.class.getName()));
        intended(hasExtraWithKey(RecipeDetailActivity.RECIPE_KEY));
    }
}
