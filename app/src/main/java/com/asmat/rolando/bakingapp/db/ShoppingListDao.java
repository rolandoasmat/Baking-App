package com.asmat.rolando.bakingapp.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by rolandoasmat on 8/14/17.
 */


@Dao
public interface ShoppingListDao {

    @Query("SELECT * FROM shopping_list")
    List<ShoppingListIngredient> getAllIngredients();

    @Query("SELECT * FROM shopping_list WHERE recipe LIKE :recipeName")
    List<ShoppingListIngredient> getAllIngredientsOfRecipe(String recipeName);

    @Query("SELECT * FROM shopping_list WHERE recipe LIKE :recipeName AND ingredient LIKE :ingredientName LIMIT 1")
    ShoppingListIngredient findIngredient(String recipeName,  String ingredientName);

    @Insert(
            onConflict = 1
    )
    void insert( ShoppingListIngredient var1);

    @Insert(
            onConflict = 1
    )
    void insertAll(List<ShoppingListIngredient> var1);

    @Delete
    void delete(ShoppingListIngredient var1);
}
