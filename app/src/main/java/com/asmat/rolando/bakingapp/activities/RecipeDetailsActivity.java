package com.asmat.rolando.bakingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.asmat.rolando.bakingapp.R;
import com.asmat.rolando.bakingapp.adapters.IngredientsAdapter;
import com.asmat.rolando.bakingapp.adapters.RecipeDetailsViewPagerAdapter;
import com.asmat.rolando.bakingapp.fragments.IngredientsFragment;
import com.asmat.rolando.bakingapp.fragments.StepsFragment;
import com.asmat.rolando.bakingapp.models.Recipe;

/**
 * Created by rolandoasmat on 8/7/17.
 */

public class RecipeDetailsActivity extends AppCompatActivity 
        implements IngredientsFragment.OnIngredientsFragmentInteractionListener, StepsFragment.OnStepsFragmentInteractionListener {
    
    private Recipe mRecipe;
    private RecipeDetailsViewPagerAdapter mViewPagerAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mRecipe = getIntent().getParcelableExtra(MainActivity.INTENT_EXTRA_RECIPE);
        getSupportActionBar().setTitle(mRecipe.getName());
        mViewPagerAdapter = new RecipeDetailsViewPagerAdapter(getSupportFragmentManager(), this);
        mViewPagerAdapter.setRecipe(mRecipe);
        ViewPager viewPager = (ViewPager) findViewById(R.id.container);
        viewPager.setAdapter(mViewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onIngredientTapped(IngredientsAdapter.ViewHolder item) {
        if(item.getCheckedTextView().isEnabled()) {
            IngredientsAdapter.markAsChecked(item.getCheckedTextView());
            IngredientsAdapter.addToDatabase(item.getRecipe().getName(), item.getCheckedTextView().getText().toString(), false, this);
        } else {
            IngredientsAdapter.markAsUnChecked(item.getCheckedTextView());
            IngredientsAdapter.addToDatabase(item.getRecipe().getName(), item.getCheckedTextView().getText().toString(), true, this);
        }
    }

    @Override
    public void onBeginRecipe(View view) {
        Intent intent = new Intent(this, RecipeStepsActivity.class);
        intent.putExtra(MainActivity.INTENT_EXTRA_RECIPE, mRecipe);
        startActivity(intent);
    }
}
