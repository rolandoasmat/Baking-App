package com.asmat.rolando.bakingapp.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.asmat.rolando.bakingapp.R;
import com.asmat.rolando.bakingapp.adapters.RecipeStepsViewPagerAdapter;
import com.asmat.rolando.bakingapp.models.Recipe;

/**
 * Created by rolandoasmat on 8/7/17.
 */

public class RecipeStepsActivity extends AppCompatActivity {
    
    private Recipe mRecipe;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_steps);

        TabLayout tabLayout = findViewById(R.id.steps_tab_layout);
        mRecipe = getIntent().getParcelableExtra(MainActivity.INTENT_EXTRA_RECIPE);
        getSupportActionBar().setTitle(mRecipe.getName());

        RecipeStepsViewPagerAdapter viewPagerAdapter = new RecipeStepsViewPagerAdapter(getSupportFragmentManager(),this);
        viewPagerAdapter.setRecipe(mRecipe);

        ViewPager viewPager = findViewById(R.id.steps_view_pager);
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
