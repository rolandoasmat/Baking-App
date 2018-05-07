package com.asmat.rolando.rocoton.adapters

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.asmat.rolando.rocoton.R
import com.asmat.rolando.rocoton.fragments.CompleteStepFragment
import com.asmat.rolando.rocoton.models.Recipe

/**
 * Created by rolandoasmat on 7/14/17.
 */
// https://github.com/google/ExoPlayer/issues/591
class RecipeStepsViewPagerAdapter(fm: FragmentManager, var mContext: Context) : FragmentStatePagerAdapter(fm) {
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
            return mContext.resources.getString(R.string.intro)
        } else {
            return id.toString()
        }
    }
}