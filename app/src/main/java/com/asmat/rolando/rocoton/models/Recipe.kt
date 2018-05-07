package com.asmat.rolando.rocoton.models

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by rolandoasmat on 7/1/17.
 */
class Recipe: Parcelable {
    val id: Int
    val name: String
    val ingredients: Array<Ingredient>
    val steps: Array<Step>
    val servings: Int
    val image: String

    constructor(id: Int, name: String, ingredients: Array<Ingredient>, steps: Array<Step>, servings: Int, image: String) {
        this.id = id
        this.name = name
        this.ingredients = ingredients
        this.steps = steps
        this.servings = servings
        this.image = image
    }

    constructor(source: Parcel) {
        id = source.readInt()
        name = source.readString()
        ingredients = source.createTypedArray(Ingredient.CREATOR)
        steps = source.createTypedArray(Step.CREATOR)
        servings = source.readInt()
        image = source.readString()
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        if(dest != null) {
            dest.writeInt(id)
            dest.writeString(name)
            dest.writeTypedArray(ingredients, 0)
            dest.writeTypedArray(steps, 0)
            dest.writeInt(servings)
            dest.writeString(image)
        }
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        @JvmField final val CREATOR: Parcelable.Creator<Recipe> = object : Parcelable.Creator<Recipe> {
            override fun createFromParcel(source: Parcel): Recipe {
                return Recipe(source)
            }

            override fun newArray(size: Int): Array<Recipe?> {
                return arrayOfNulls(size)
            }
        }
    }
}