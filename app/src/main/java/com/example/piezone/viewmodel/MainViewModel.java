package com.example.piezone.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.piezone.data.RecipeRepository;
import com.example.piezone.model.Recipe;

import java.util.List;

import io.reactivex.schedulers.Schedulers;

public class MainViewModel extends ViewModel{
    private RecipeRepository mRepository;
    private MutableLiveData<List<Recipe>> mRecipes = new MutableLiveData<>();

    public MainViewModel() {
        mRepository = new RecipeRepository();
        mRepository.getRecipes().subscribeOn(Schedulers.io()).doOnSuccess(recipes -> {
            mRecipes.postValue(recipes);
        }).subscribe();

    }

    public MutableLiveData<List<Recipe>> getRecipes() { return mRecipes; }
}
