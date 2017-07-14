package com.asmat.rolando.bakingapp.activities

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.asmat.rolando.bakingapp.R
import com.asmat.rolando.bakingapp.adapters.RecipeStepsViewPagerAdapter
import com.asmat.rolando.bakingapp.models.Recipe

class RecipeStepsActivity : AppCompatActivity() {
    var mRecipe: Recipe? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_steps)

        val tabLayout = findViewById(R.id.steps_tab_layout) as TabLayout
        mRecipe = intent.getParcelableExtra<Recipe>(MainActivity.ARG_RECIPE)
        supportActionBar?.title = mRecipe?.name
        val viewPagerAdapter = RecipeStepsViewPagerAdapter(supportFragmentManager)
        viewPagerAdapter.recipe = mRecipe
        val viewPager = findViewById(R.id.steps_view_pager) as ViewPager
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
}
