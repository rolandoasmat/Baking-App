package com.asmat.rolando.bakingapp.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by rolandoasmat on 8/14/17.
 */

public class Ingredient implements Parcelable {
    private final double quantity;
    
    private final String measure;
    
    private final String ingredientName;

    public Ingredient(double quantity,  String measure,  String ingredientName) {
        this.quantity = quantity;
        this.measure = measure;
        this.ingredientName = ingredientName;
    }

    public final String createListEntry() {
        return Double.toString(quantity).replace(".0", "") + " " + measure + " of " + ingredientName;
    }

    public Ingredient(Parcel source) {
        this.quantity = source.readDouble();
        this.measure = source.readString();
        this.ingredientName = source.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if(dest != null) {
            dest.writeDouble(this.quantity);
            dest.writeString(this.measure);
            dest.writeString(this.ingredientName);
        }
    }

    public int describeContents() {
        return 0;
    }
    
    public static final Creator CREATOR = (Creator)(new Creator() {

        @Override
        public Ingredient createFromParcel(Parcel source) {
            return new Ingredient(source);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }

    });
}
