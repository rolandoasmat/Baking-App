package com.asmat.rolando.bakingapp.db

import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.content.Context


/**
 * Created by rolandoasmat on 7/16/17.
 */

@Database(entities = arrayOf(IngredientDB::class), version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun ingredientsDao(): IngredientDao

    companion object{
        private val databaseName = "ingredients_db"

        var dbInstance:IngredientDao? = null
        fun getInstance(context: Context):IngredientDao?{
            if(dbInstance == null)
                dbInstance = Room.databaseBuilder(context, AppDatabase::class.java, databaseName).build().ingredientsDao()
            return dbInstance;
        }
    }
}