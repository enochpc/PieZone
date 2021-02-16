package com.example.piezone.data;

import java.util.List;

import io.reactivex.Single;

import com.example.piezone.model.Recipe;

public class RecipeRepository {
    private RecipeApi mRecipeApi;

    public RecipeRepository(){
        mRecipeApi = RecipeService.getRetrofitInstance().create(RecipeApi.class);

    }

    public Single<List<Recipe>> getRecipes() {return mRecipeApi.getRecipes();}

}
