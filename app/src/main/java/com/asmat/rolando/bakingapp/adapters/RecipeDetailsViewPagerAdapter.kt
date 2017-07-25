package com.asmat.rolando.bakingapp.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter
import android.view.ViewGroup
import com.asmat.rolando.bakingapp.fragments.IngredientsFragment
import com.asmat.rolando.bakingapp.fragments.StepsFragment
import com.asmat.rolando.bakingapp.models.Recipe


/**
 * Created by rolandoasmat on 7/12/17.
 */


class RecipeDetailsViewPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    var recipe: Recipe? = null

    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> return IngredientsFragment.newInstance(recipe!!)
            1 -> return StepsFragment.newInstance(recipe!!.steps)
            else -> throw RuntimeException("View pager should only be of size 2!")
        }
    }

    override fun getPageTitle(position: Int): CharSequence {
        when (position) {
            0 -> return "Ingredients"
            1 -> return "Steps"
            else -> throw RuntimeException("View pager should only be of size 2!")
        }
    }

    // https://stackoverflow.com/questions/7263291/viewpager-pageradapter-not-updating-the-view
    override fun getItemPosition(`object`: Any?): Int {
        return PagerAdapter.POSITION_NONE
    }

    override fun instantiateItem(container: ViewGroup?, position: Int): Any {
        return super.instantiateItem(container, position)
    }

    override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any?) {
        super.destroyItem(container, position, `object`)
    }
}