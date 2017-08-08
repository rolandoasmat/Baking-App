package com.asmat.rolando.bakingapp.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;


/**
 * Created by rolandoasmat on 8/7/17.
 */

@Database(entities = {ShoppingListIngredient.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static final String databaseName = "shopping_list_db";
    
    private static ShoppingListDao dbInstance;

    public abstract ShoppingListDao shoppingListDao();

    public static ShoppingListDao getInstance(Context context) {
        if(dbInstance == null) {
            dbInstance = Room.databaseBuilder(context, AppDatabase.class, databaseName).build().shoppingListDao();
        }
        return dbInstance;
    }
}
