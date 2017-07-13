package com.asmat.rolando.bakingapp.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.asmat.rolando.bakingapp.fragments.IngredientsFragment
import com.asmat.rolando.bakingapp.models.Recipe


/**
 * Created by rolandoasmat on 7/12/17.
 */


class RecipeDetailsViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    var recipe: Recipe? = null

    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {
        return IngredientsFragment.newInstance(recipe!!.ingredients)
    }
}