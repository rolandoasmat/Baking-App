package com.asmat.rolando.rocoton.db

import android.arch.persistence.room.*


/**
 * Created by rolandoasmat on 7/16/17.
 */

@Dao
interface ShoppingListDao {

    @Query("SELECT * FROM shopping_list")
    fun getAllIngredients(): List<ShoppingListIngredient>

    @Query("SELECT * FROM shopping_list WHERE recipe LIKE :recipe")
    fun getAllIngredientsOfRecipe(recipe:String): List<ShoppingListIngredient>

    @Query("SELECT * FROM shopping_list WHERE recipe LIKE :recipe AND ingredient LIKE :ingredient LIMIT 1")
    fun findIngredient(recipe: String, ingredient: String): ShoppingListIngredient

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(shoppingListIngredient: ShoppingListIngredient)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(ingredients: List<ShoppingListIngredient>)

    @Delete
    fun delete(ingredient: ShoppingListIngredient)
}