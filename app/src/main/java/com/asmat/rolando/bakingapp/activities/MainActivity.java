package com.asmat.rolando.bakingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.asmat.rolando.bakingapp.R;
import com.asmat.rolando.bakingapp.adapters.IngredientsAdapter;
import com.asmat.rolando.bakingapp.adapters.RecipeDetailsViewPagerAdapter;
import com.asmat.rolando.bakingapp.fragments.IngredientsFragment;
import com.asmat.rolando.bakingapp.fragments.RecipesListFragment;
import com.asmat.rolando.bakingapp.fragments.StepsFragment;
import com.asmat.rolando.bakingapp.models.Recipe;


/**
 * Created by rolandoasmat on 8/5/17.
 */

public class MainActivity extends AppCompatActivity
        implements RecipesListFragment.OnRecipeClickListener, IngredientsFragment.OnIngredientsFragmentInteractionListener,
        StepsFragment.OnStepsFragmentInteractionListener {

    public boolean mIsDualPane = false;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private RecipeDetailsViewPagerAdapter mViewPagerAdapter;
    private Recipe mSelectedRecipe;

    public static final String INTENT_EXTRA_RECIPE = "intent_extra_recipe";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View view = findViewById(R.id.activity_recipe_details_layout);
        mIsDualPane = view != null && view.getVisibility() == View.VISIBLE;
        if(mIsDualPane) {
            mTabLayout = this.findViewById(R.id.tab_layout);
            mViewPager = this.findViewById(R.id.container);
            mViewPagerAdapter = new RecipeDetailsViewPagerAdapter(getSupportFragmentManager(), this);
            if(mTabLayout != null) {
                mTabLayout.setupWithViewPager(mViewPager);
            }
        }
    }

    @Override
    public void onRecipeSelected(Recipe recipe) {
        if(!mIsDualPane) {
            Intent intent = new Intent(this, RecipeDetailsActivity.class);
            intent.putExtra(INTENT_EXTRA_RECIPE, recipe);
            startActivity(intent);
        } else {
            mSelectedRecipe = recipe;
            setupDetailView(recipe);
        }
    }

    public final void setupDetailView(Recipe recipe) {
        getSupportActionBar().setTitle(recipe.getName());
        mViewPagerAdapter.setRecipe(recipe);
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onIngredientTapped(IngredientsAdapter.ViewHolder item) {
        System.out.print(item);
    }

    public void onBeginRecipe(View view) {
        Intent intent = new Intent(this, RecipeStepsActivity.class);
        intent.putExtra(INTENT_EXTRA_RECIPE, mSelectedRecipe);
        startActivity(intent);
    }
}