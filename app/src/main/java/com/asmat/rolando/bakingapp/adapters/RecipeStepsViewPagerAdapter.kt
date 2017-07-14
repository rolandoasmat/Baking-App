package com.asmat.rolando.bakingapp.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.asmat.rolando.bakingapp.fragments.CompleteStepFragment
import com.asmat.rolando.bakingapp.models.Recipe

/**
 * Created by rolandoasmat on 7/14/17.
 */

class RecipeStepsViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    var recipe: Recipe? = null

    override fun getCount(): Int {
        val count = recipe?.steps?.size
        if(count != null) {
            return count
        } else {
            return 0
        }
    }

    override fun getItem(position: Int): Fragment {
        return CompleteStepFragment.newInstance(recipe!!, position)
    }

    override fun getPageTitle(position: Int): CharSequence {
        val id =  recipe!!.steps[position].id
        if(id == 0) {
            return "Intro"
        } else {
            return id.toString()
        }
    }
}