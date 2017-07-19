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
import com.asmat.rolando.bakingapp.models.Recipe
import com.asmat.rolando.bakingapp.db.AppDatabase
import com.asmat.rolando.bakingapp.db.IngredientDB
import android.os.AsyncTask
import com.asmat.rolando.bakingapp.adapters.IngredientsAdapter

class RecipeDetailsActivity : AppCompatActivity(),
        IngredientsFragment.OnIngredientsFragmentInteractionListener,
        StepsFragment.OnStepsFragmentInteractionListener {
    var mRecipe: Recipe? = null
    var selectedIngredients = ArrayList<IngredientDB>()
    var mViewPagerAdapter: RecipeDetailsViewPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_details)

        val tabLayout = findViewById(R.id.tab_layout) as TabLayout
        mRecipe = intent.getParcelableExtra<Recipe>(MainActivity.ARG_RECIPE)
        supportActionBar?.title = mRecipe?.name
        mViewPagerAdapter = RecipeDetailsViewPagerAdapter(supportFragmentManager)
        mViewPagerAdapter!!.recipe = mRecipe
        val viewPager = findViewById(R.id.container) as ViewPager
        viewPager.adapter = mViewPagerAdapter
        tabLayout.setupWithViewPager(viewPager)
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

    // Fragment Callbacks
    override fun onIngredientTapped(item: IngredientsAdapter.ViewHolder) {
        if(item.checkedTextView.isEnabled){
            // Item was not checked, so: insert to DB and disable it
            val ingredient = item.ingredient!!
            val entry = ingredient.quantity.toString().replace(".0", "") + " " + ingredient.measure + " of " + ingredient.ingredientName
            val ingredientDB = IngredientDB(entry)
            val db = AppDatabase.getInstance(baseContext)
            object : AsyncTask<Void, Void, Int>() {
                override fun doInBackground(vararg params: Void): Int {
                    db?.insert(ingredientDB)
                    return 0
                }
            }.execute()
            IngredientsAdapter.markAsChecked(item.checkedTextView)
        } else {
            // Item was checked, so: remove from DB enable it
        }
    }

    override fun onAddGroceries(view: View) {
        print(view)
    }

    override fun onBeginRecipe(view: View) {
        val intent = Intent(this, RecipeStepsActivity::class.java)
        intent.putExtra(MainActivity.ARG_RECIPE, mRecipe)
        startActivity(intent)
    }
}