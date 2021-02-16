package com.example.piezone.data;

import java.util.List;

import com.example.piezone.model.Recipe;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface RecipeApi {
    @GET("baking.json")
    Single<List<Recipe>> getRecipes();
}
