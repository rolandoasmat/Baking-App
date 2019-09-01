package com.asmat.rolando.bakingapp.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.asmat.rolando.bakingapp.R
import com.asmat.rolando.bakingapp.adapters.RecipeStepsViewPagerAdapter
import com.asmat.rolando.bakingapp.models.Recipe
import com.google.android.material.tabs.TabLayout

class RecipeStepsActivity : AppCompatActivity() {
    var mRecipe: Recipe? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_steps)

        val tabLayout = findViewById(R.id.steps_tab_layout) as TabLayout
        mRecipe = intent.getParcelableExtra<Recipe>(MainActivity.INTENT_EXTRA_RECIPE)
        supportActionBar?.title = mRecipe?.name
        val viewPagerAdapter = RecipeStepsViewPagerAdapter(supportFragmentManager, this)
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
