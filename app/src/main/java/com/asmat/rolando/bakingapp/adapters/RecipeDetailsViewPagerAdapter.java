package com.asmat.rolando.bakingapp.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.ViewGroup;

import com.asmat.rolando.bakingapp.R;
import com.asmat.rolando.bakingapp.fragments.IngredientsFragment;
import com.asmat.rolando.bakingapp.fragments.StepsFragment;
import com.asmat.rolando.bakingapp.models.Recipe;

/**
 * Created by rolandoasmat on 8/7/17.
 */

public class RecipeDetailsViewPagerAdapter extends FragmentStatePagerAdapter {

    private Recipe recipe;
    private Context mContext;
    
    public final Recipe getRecipe() {
        return this.recipe;
    }

    public final void setRecipe( Recipe var1) {
        this.recipe = var1;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0:
                return IngredientsFragment.newInstance(recipe);
            case 1:
                return StepsFragment.newInstance(recipe.getSteps());
            default:
                throw new RuntimeException("View pager should only be of size 2!");
        }
    }
    
    public CharSequence getPageTitle(int position) {
        switch(position) {
            case 0:
                return mContext.getResources().getString(R.string.ingredients);
            case 1:
                return mContext.getResources().getString(R.string.steps);
            default:
                throw new RuntimeException("View pager should only be of size 2!");
        }
    }

    // https://stackoverflow.com/questions/7263291/viewpager-pageradapter-not-updating-the-view
    @Override
    public int getItemPosition( Object object) {
        return PagerAdapter.POSITION_NONE;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public void destroyItem( ViewGroup container, int position,  Object object) {
        super.destroyItem(container, position, object);
    }

    public RecipeDetailsViewPagerAdapter( FragmentManager fm,  Context mContext) {
        super(fm);
        this.mContext = mContext;
    }
}
