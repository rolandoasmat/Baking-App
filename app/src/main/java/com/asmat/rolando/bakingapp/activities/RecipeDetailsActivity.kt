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
import com.asmat.rolando.bakingapp.adapters.IngredientsAdapter

class RecipeDetailsActivity : AppCompatActivity(),
        IngredientsFragment.OnIngredientsFragmentInteractionListener,
        StepsFragment.OnStepsFragmentInteractionListener {
    var mRecipe: Recipe? = null
    var mViewPagerAdapter: RecipeDetailsViewPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_details)

        val tabLayout = findViewById(R.id.tab_layout) as TabLayout
        mRecipe = intent.getParcelableExtra<Recipe>(MainActivity.INTENT_EXTRA_RECIPE)
        supportActionBar?.title = mRecipe?.name
        mViewPagerAdapter = RecipeDetailsViewPagerAdapter(supportFragmentManager, this)
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
            // Item was not checked, so user is marking this ingredient as gotten. Mark as no longer needed
            IngredientsAdapter.markAsChecked(item.checkedTextView)
            IngredientsAdapter.addToDatabase(item.recipe!!.name, item.checkedTextView.text.toString(), false,this)
        } else {
            // Item was checked, so: remove from DB enable it
            IngredientsAdapter.markAsUnChecked(item.checkedTextView)
            IngredientsAdapter.addToDatabase(item.recipe!!.name, item.checkedTextView.text.toString(), true,this)
        }
    }

    override fun onBeginRecipe(view: View) {
        val intent = Intent(this, RecipeStepsActivity::class.java)
        intent.putExtra(MainActivity.INTENT_EXTRA_RECIPE, mRecipe)
        startActivity(intent)
    }
}