package com.example.piezone.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.piezone.R;
import com.example.piezone.model.Recipe;
import com.example.piezone.viewmodel.RecipeDetailViewModel;
import com.example.piezone.widget.RecipeWidgetService;
import com.example.piezone.ui.StepListFragment;


public class RecipeDetailActivity extends AppCompatActivity{
    public static final String RECIPE_KEY = "RECIPE_DETAIL_KEY";

    private RecipeDetailViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        Recipe recipe = getIntent().getParcelableExtra(RECIPE_KEY);
        setTitle(recipe.getName());

        //Setting the recipe for the shared viewmodel so the fragments can access it
        mViewModel = ViewModelProviders.of(this).get(RecipeDetailViewModel.class);
        mViewModel.setRecipe(recipe);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Recipe recipe = intent.getParcelableExtra(RECIPE_KEY);
        setTitle(recipe.getName());

        //Setting the recipe for the shared viewmodel so the fragments can access it
        mViewModel = ViewModelProviders.of(this).get(RecipeDetailViewModel.class);
        mViewModel.setRecipe(recipe);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_recipe_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Save the selected recipe to preferences for the widget to load
        if(item.getItemId() == R.id.mi_add_to_widget) {
            RecipeWidgetService.updateWidget(this, mViewModel.getRecipe().getValue());
        }

        return false;
    }
}
