package com.asmat.rolando.bakingapp.db

import android.arch.persistence.room.*


/**
 * Created by rolandoasmat on 7/16/17.
 */

@Dao
interface IngredientDao {

    @Query("SELECT * FROM ingredients")
    fun getAllIngredients(): List<IngredientDB>

    @Query("SELECT * FROM ingredients WHERE ingredient LIKE :arg0 LIMIT 1")
    fun findByName(ingredient: String): IngredientDB

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(ingredient: IngredientDB)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(ingredients: List<IngredientDB>)

    @Delete
    fun delete(ingredient: IngredientDB)
}