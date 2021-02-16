package com.example.piezone.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.piezone.model.Recipe;
import com.example.piezone.model.Step;


public class RecipeDetailViewModel extends ViewModel{

    private MutableLiveData<Recipe> mRecipe = new MutableLiveData<>();
    private MutableLiveData<Step> mStep = new MutableLiveData<>();

    public RecipeDetailViewModel() {

    }

    public MutableLiveData<Recipe> getRecipe() { return mRecipe; }

    public void setRecipe(Recipe recipe) { this.mRecipe.setValue(recipe); }

    public MutableLiveData<Step> getStep() { return mStep; }

    public void setStep(Step step) { mStep.setValue(step); }

}
