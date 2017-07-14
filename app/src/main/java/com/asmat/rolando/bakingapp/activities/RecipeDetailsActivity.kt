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

class RecipeDetailsActivity : AppCompatActivity(),
        IngredientsFragment.OnListFragmentInteractionListener,
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

    override fun onBeginRecipe(view: View) {
        val intent = Intent(this, RecipeStepsActivity::class.java)
        intent.putExtra(MainActivity.ARG_RECIPE, mRecipe)
        startActivity(intent)
    }

    override fun onListFragmentInteraction(item: Ingredient) {
        print(item)
    }
}
