package com.asmat.rolando.bakingapp.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.asmat.rolando.bakingapp.R
import com.asmat.rolando.bakingapp.models.Recipe

class RecipeDetails : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_details)
        val recipe = intent.getParcelableExtra<Recipe>(MainActivity.ARG_RECIPE)
        supportActionBar?.title = recipe.name
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                super.onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
