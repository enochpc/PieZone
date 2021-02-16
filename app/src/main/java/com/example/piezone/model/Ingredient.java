package com.example.piezone.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.text.DecimalFormat;

public class Ingredient implements Parcelable{

    private float quantity;
    private String measure;
    private String ingredient;

    protected Ingredient(Parcel in){
        quantity = in.readFloat();
        measure = in.readString();
        ingredient = in.readString();

    }

    public float getQuantity() { return quantity;}

    public String getMeasure() { return measure;}

    public String getIngredient() { return ingredient;}

    @NonNull
    @Override
    public String toString() {
        return ingredient + " (" + new DecimalFormat("#.##").format(quantity) + "\u00A0" + measure + ")";

    }

    @Override
    public int describeContents() { return 0;}

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(quantity);
        dest.writeString(measure);
        dest.writeString(ingredient);

    }

    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel source) {
            return new Ingredient(source);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };




}
