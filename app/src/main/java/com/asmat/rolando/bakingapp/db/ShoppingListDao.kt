package com.asmat.rolando.bakingapp.db

import android.arch.persistence.room.*


/**
 * Created by rolandoasmat on 7/16/17.
 */

@Dao
interface ShoppingListDao {

    @Query("SELECT * FROM shopping_list")
    fun getAllIngredients(): List<ShoppingListIngredient>

    @Query("SELECT * FROM shopping_list WHERE recipe LIKE :arg0")
    fun getAllIngredientsOfRecipe(recipe:String): List<ShoppingListIngredient>

    @Query("SELECT * FROM shopping_list WHERE recipe LIKE :arg0 AND ingredient LIKE :arg1 LIMIT 1")
    fun findIngredient(recipe: String, ingredient: String): ShoppingListIngredient

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(shoppingListIngredient: ShoppingListIngredient)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(ingredients: List<ShoppingListIngredient>)

    @Delete
    fun delete(ingredient: ShoppingListIngredient)
}