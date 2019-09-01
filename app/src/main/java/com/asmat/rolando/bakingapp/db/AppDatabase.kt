package com.asmat.rolando.bakingapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


/**
 * Created by rolandoasmat on 7/16/17.
 */

@Database(entities = arrayOf(ShoppingListIngredient::class), version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun shoppingListDao(): ShoppingListDao

    companion object{
        private val databaseName = "shopping_list_db"
        var dbInstance: ShoppingListDao? = null

        fun getInstance(context: Context): ShoppingListDao? {
            if(dbInstance == null)
                // https://stackoverflow.com/questions/46665621/android-room-persistent-appdatabase-impl-does-not-exist
                dbInstance = Room.databaseBuilder(context, AppDatabase::class.java, databaseName).build().shoppingListDao()
            return dbInstance
        }
    }
}