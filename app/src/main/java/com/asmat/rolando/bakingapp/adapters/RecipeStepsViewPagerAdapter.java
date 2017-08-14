package com.asmat.rolando.bakingapp.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.asmat.rolando.bakingapp.R;
import com.asmat.rolando.bakingapp.fragments.CompleteStepFragment;
import com.asmat.rolando.bakingapp.models.Recipe;

/**
 * Created by rolandoasmat on 8/13/17.
 */

public class RecipeStepsViewPagerAdapter extends FragmentStatePagerAdapter {
    private Recipe recipe;
    private Context mContext;

    @Override
    public int getCount() {
        return this.recipe.getSteps().length;
    }

    @Override
    public Fragment getItem(int position) {
        return CompleteStepFragment.Companion.newInstance(recipe, position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        int id = this.recipe.getSteps()[position].getId();
        if(id == 0) {
            return this.mContext.getResources().getString(R.string.intro);
        } else {
            return String.valueOf(id);
        }
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public RecipeStepsViewPagerAdapter(FragmentManager fm, Context mContext) {
        super(fm);
        this.mContext = mContext;
    }
}
