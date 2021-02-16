package com.example.piezone.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.bumptech.glide.Glide;

import com.example.piezone.model.Recipe;
import com.example.piezone.viewmodel.MainViewModel;
import butterknife.BindView;
import butterknife.ButterKnife;

import com.example.piezone.R;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rv_recipes)
    RecyclerView recipeRecyclerView;
    RecipeAdapter mAdapter;

    private MainViewModel mMainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mMainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        mAdapter = new RecipeAdapter(Glide.with(this));
        recipeRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(position -> {
            //Open the detail of the clicked Recipe
            if(mMainViewModel.getRecipes().getValue() != null) {
                Recipe recipe = mMainViewModel.getRecipes().getValue().get(position);
                Intent recipeDetailIntent = new Intent(this, RecipeDetailActivity.class);
                recipeDetailIntent.putExtra(RecipeDetailActivity.RECIPE_KEY, recipe);
                startActivity(recipeDetailIntent);
            }
        });

        mMainViewModel.getRecipes().observe(this, recipes -> {
            mAdapter.setRecipes(recipes);
        });

    }
}