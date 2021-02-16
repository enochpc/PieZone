package com.example.piezone.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.List;

public class Recipe implements Parcelable{

    private int id;
    private String name;
    private int servings;
    private String image;
    private List<Ingredient> ingredients;
    private List<Step> steps;

    protected Recipe(Parcel in){
        id = in.readInt();
        name = in.readString();
        servings = in.readInt();
        image = in.readString();
        ingredients = in.createTypedArrayList(Ingredient.CREATOR);
        steps = in.createTypedArrayList(Step.CREATOR);
    }

    public int getId() { return id; }

    public String getName() { return name; }

    public int getServings() { return servings; }

    public String getImage() { return image; }

    public List<Ingredient> getIngredients() { return ingredients; }

    public List<Step> getSteps() { return steps; }

    @NonNull
    @Override
    public String toString() {
        return id + ": " + name + "\n" + ingredients.toString() + "\n" + steps.toString();
    }

    public String getIngredientString(){
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < ingredients.size(); i++) {
            result.append("\u2022 ")
                    .append(ingredients.get(i));

            if(i < ingredients.size() - 1) {
                result.append("\n");
            }
        }

        return result.toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeInt(servings);
        dest.writeString(image);
        dest.writeTypedList(ingredients);
        dest.writeTypedList(steps);
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

}
