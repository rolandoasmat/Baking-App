package com.asmat.rolando.bakingapp.db

import android.arch.persistence.room.Delete
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query


/**
 * Created by rolandoasmat on 7/16/17.
 */

@Dao
interface IngredientDao {

    @Query("SELECT * FROM ingredients")
    fun getAllIngredients(): List<IngredientDB>

    @Query("SELECT * FROM ingredients WHERE ingredient LIKE :arg0 LIMIT 1")
    fun findByName(ingredient: String): IngredientDB

    @Insert
    fun insert(ingredient: IngredientDB)

    @Delete
    fun delete(ingredient: IngredientDB)
}