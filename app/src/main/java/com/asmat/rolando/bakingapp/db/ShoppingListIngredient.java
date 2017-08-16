package com.asmat.rolando.bakingapp.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by rolandoasmat on 8/14/17.
 */

@Entity(tableName = "shopping_list")
public class ShoppingListIngredient {
    @ColumnInfo(
            name = "id"
    )
    @PrimaryKey(
            autoGenerate = true
    )
    private long id;
    @ColumnInfo(
            name = "recipe"
    )
    
    private String recipe;
    @ColumnInfo(
            name = "needed"
    )
    private boolean needed;
    @ColumnInfo(
            name = "ingredient"
    )
    
    private String ingredient;

    public final long getId() {
        return this.id;
    }

    public final void setId(long var1) {
        this.id = var1;
    }

    
    public final String getRecipe() {
        return this.recipe;
    }

    public final void setRecipe( String var1) {
        this.recipe = var1;
    }

    public final boolean getNeeded() {
        return this.needed;
    }

    public final void setNeeded(boolean var1) {
        this.needed = var1;
    }

    
    public final String getIngredient() {
        return this.ingredient;
    }

    public final void setIngredient( String var1) {
        this.ingredient = var1;
    }

    public ShoppingListIngredient( String ingredient, String recipe, boolean needed) {
        this.ingredient = ingredient;
        this.recipe = recipe;
        this.needed = needed;
    }
    @Ignore
    public ShoppingListIngredient( String ingredient) {
        this.ingredient = ingredient;
        this.recipe = "";
        this.needed = false;
    }

    @Ignore
    public ShoppingListIngredient() {
        this.ingredient = "";
        this.recipe = "";
        this.needed = false;
    }
}
