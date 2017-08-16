package com.asmat.rolando.bakingapp.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by rolandoasmat on 8/15/17.
 */

public class Recipe implements Parcelable {
    private final int id;
    
    private final String name;
    
    private final Ingredient[] ingredients;
    
    private final Step[] steps;
    private final int servings;
    
    private final String image;

    public Recipe(int id,  String name,  Ingredient[] ingredients,  Step[] steps, int servings,  String image) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.servings = servings;
        this.image = image;
    }

    public Recipe(Parcel source) {
        this.id = source.readInt();
        this.name = source.readString();
        this.ingredients = (Ingredient[]) source.createTypedArray(Ingredient.CREATOR);
        this.steps = (Step[]) source.createTypedArray(Step.CREATOR);
        this.servings = source.readInt();
        this.image = source.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if(dest != null) {
            dest.writeInt(this.id);
            dest.writeString(this.name);
            dest.writeTypedArray(this.ingredients, 0);
            dest.writeTypedArray(this.steps, 0);
            dest.writeInt(this.servings);
            dest.writeString(this.image);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }
    
    public static final Creator CREATOR = (Creator)(new Creator() {
        @Override
        public Recipe createFromParcel( Parcel source) {
            return new Recipe(source);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    });

    public final int getId() {
        return this.id;
    }

    public final String getName() {
        return this.name;
    }
    
    public final Ingredient[] getIngredients() {
        return this.ingredients;
    }
    
    public final Step[] getSteps() {
        return this.steps;
    }

    public final String getImage() {
        return this.image;
    }
}
