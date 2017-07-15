package com.asmat.rolando.bakingapp.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.ListFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import com.asmat.rolando.bakingapp.R
import com.asmat.rolando.bakingapp.RecipesApiManager
import com.asmat.rolando.bakingapp.Utils.ArrayUtils
import com.asmat.rolando.bakingapp.activities.MainActivity
import com.asmat.rolando.bakingapp.adapters.RecipesAdapter
import com.asmat.rolando.bakingapp.models.Recipe

/**
 * Created by rolandoasmat on 7/4/17.
 */

class RecipesListFragment: ListFragment(), OnItemClickListener {
    var callback: OnRecipeClickListener? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_recipes, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        listAdapter = RecipesAdapter(activity, R.layout.fragment_recipes_list_item, ArrayList<Recipe>())
        listView.onItemClickListener = this
        fetchRecipes()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if(context is OnRecipeClickListener) {
            callback = context
        } else {
            throw ClassCastException("Activity does not implement OnRecipeClickListener")
        }
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if(callback != null) {
            val recipe = (listAdapter as RecipesAdapter).getItem(position)
            callback?.onRecipeSelected(recipe)
        }
    }

    private fun fetchRecipes() {
        RecipesApiManager.fetchRecipes(activity){ recipes ->
            val recipesAdapter = listAdapter as RecipesAdapter
            if(recipes != null) {
                val arrayList = ArrayUtils.toArrayList(recipes)
                recipesAdapter.addAll(arrayList)
                if(context is MainActivity) {
                    val main = context as MainActivity
                    if(main.mIsDualPane) {
                        callback?.onRecipeSelected(arrayList[0])
                    }
                }
            } else {
                recipesAdapter.clear()
            }
        }
    }

    interface OnRecipeClickListener {
        fun onRecipeSelected(recipe: Recipe)
    }
}