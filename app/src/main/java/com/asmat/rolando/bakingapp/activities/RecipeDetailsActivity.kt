package com.asmat.rolando.bakingapp.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.view.MenuItem
import android.view.View
import com.asmat.rolando.bakingapp.R
import com.asmat.rolando.bakingapp.adapters.RecipeDetailsViewPagerAdapter
import com.asmat.rolando.bakingapp.fragments.IngredientsFragment
import com.asmat.rolando.bakingapp.fragments.StepsFragment
import com.asmat.rolando.bakingapp.models.Ingredient
import com.asmat.rolando.bakingapp.models.Recipe
import com.asmat.rolando.bakingapp.db.AppDatabase
import com.asmat.rolando.bakingapp.db.IngredientDB
import android.os.AsyncTask

class RecipeDetailsActivity : AppCompatActivity(),
        IngredientsFragment.OnIngredientsFragmentInteractionListener,
        StepsFragment.OnStepsFragmentInteractionListener {
    var mRecipe: Recipe? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_details)

        val tabLayout = findViewById(R.id.tab_layout) as TabLayout
        mRecipe = intent.getParcelableExtra<Recipe>(MainActivity.ARG_RECIPE)
        supportActionBar?.title = mRecipe?.name
        val viewPagerAdapter = RecipeDetailsViewPagerAdapter(supportFragmentManager)
        viewPagerAdapter.recipe = mRecipe
        val viewPager = findViewById(R.id.container) as ViewPager
        viewPager.adapter = viewPagerAdapter
        tabLayout.setupWithViewPager(viewPager)
        fetchSelectedIngredients()
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

    fun fetchSelectedIngredients() {
        val db = AppDatabase.getInstance(baseContext)
        object : AsyncTask<Void, Void, List<IngredientDB>>() {
            override fun doInBackground(vararg params: Void): List<IngredientDB> {
                return db!!.getAllIngredients()
            }

            override fun onPostExecute(result: List<IngredientDB>?) {
                for(ingredient in result!!) {
                    print(ingredient.ingredient)
                }
            }
        }.execute()
    }

    // Fragment Callbacks
    var selectedIngredients = ArrayList<IngredientDB>()
    override fun onIngredientTapped(item: Ingredient) {
        val entry = item.quantity.toString().replace(".0", "")+" "+item.measure+" "+" of "+item.ingredientName
        val ingredientDB = IngredientDB(entry)
        selectedIngredients.add(ingredientDB)
    }

    override fun onAddGroceries(view: View) {
        val db = AppDatabase.getInstance(baseContext)
        for(ingredient in selectedIngredients) {
            object : AsyncTask<Void, Void, Int>() {
                override fun doInBackground(vararg params: Void): Int {
                    db?.insert(ingredient)
                    return 0
                }
            }.execute()
        }
    }

    override fun onBeginRecipe(view: View) {
        val intent = Intent(this, RecipeStepsActivity::class.java)
        intent.putExtra(MainActivity.ARG_RECIPE, mRecipe)
        startActivity(intent)
    }
}