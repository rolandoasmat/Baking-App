package com.asmat.rolando.bakingapp.db

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey



/**
 * Created by rolandoasmat on 7/16/17.
 */

@Entity(tableName = "shopping_list")
class ShoppingListIngredient(@ColumnInfo(name = "ingredient") var ingredient: String = "")
{
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    @ColumnInfo(name = "recipe")
    var recipe: String = ""

    @ColumnInfo(name = "needed")
    var needed: Boolean = true
}