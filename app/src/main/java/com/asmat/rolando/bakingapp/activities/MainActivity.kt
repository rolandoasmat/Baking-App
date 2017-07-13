package com.asmat.rolando.bakingapp.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.asmat.rolando.bakingapp.R
import com.asmat.rolando.bakingapp.fragments.RecipesListFragment
import com.asmat.rolando.bakingapp.models.Recipe

class MainActivity : AppCompatActivity(), RecipesListFragment.OnRecipeClickListener {
    companion object {
        val ARG_RECIPE = "arg_recipe"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onRecipeSelected(recipe: Recipe) {
        val intent = Intent(this, RecipeDetailsActivity::class.java)
        intent.putExtra(ARG_RECIPE, recipe)
        startActivity(intent)
    }
}
