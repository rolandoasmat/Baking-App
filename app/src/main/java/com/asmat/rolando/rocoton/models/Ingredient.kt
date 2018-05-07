package com.asmat.rolando.rocoton.models

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by rolandoasmat on 7/1/17.
 */

class Ingredient: Parcelable {
    val quantity: Double
    val measure: String
    val ingredientName: String

    constructor(quantity: Double, measure: String, ingredientName: String) {
        this.quantity = quantity
        this.measure = measure
        this.ingredientName = ingredientName
    }

    fun createListEntry():String {
        return quantity.toString().replace(".0", "") + " " + measure + " of " + ingredientName
    }

    constructor(source: Parcel) {
        this.quantity = source.readDouble()
        this.measure = source.readString()
        this.ingredientName = source.readString()
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        if(dest != null) {
            dest.writeDouble(quantity)
            dest.writeString(measure)
            dest.writeString(ingredientName)
        }
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        @JvmField final val CREATOR: Parcelable.Creator<Ingredient> = object : Parcelable.Creator<Ingredient> {
            override fun createFromParcel(source: Parcel): Ingredient{
                return Ingredient(source)
            }

            override fun newArray(size: Int): Array<Ingredient?> {
                return arrayOfNulls(size)
            }
        }
    }
}