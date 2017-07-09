package com.asmat.rolando.bakingapp.fragments

import android.os.Bundle
import android.support.v4.app.ListFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.Toast
import com.asmat.rolando.bakingapp.R
import com.asmat.rolando.bakingapp.RecipesApiManager
import com.asmat.rolando.bakingapp.adapters.RecipesAdapter
import com.asmat.rolando.bakingapp.models.Recipe

/**
 * Created by rolandoasmat on 7/4/17.
 */

class RecipesListFragment: ListFragment(), OnItemClickListener {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (inflater != null) {
            return inflater.inflate(R.layout.recipes_list_fragment, container, false)
        } else {
            return null
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        listAdapter = RecipesAdapter(activity, R.layout.recipe_list_item, ArrayList<Recipe>())
        listView.onItemClickListener = this
        fetchRecipes()
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        Toast.makeText(activity, "Item: " + position, Toast.LENGTH_SHORT).show()
    }
    private fun fetchRecipes() {
        RecipesApiManager.fetchRecipes(activity){ recipes ->
            val recipesAdapter = listAdapter as RecipesAdapter
            if(recipes != null) {
                val arrayList = ArrayList<Recipe>()
                for(recipe in recipes){
                    arrayList.add(recipe)
                }
                recipesAdapter.addAll(arrayList)
            } else {
                recipesAdapter.clear()
            }
        }
    }

}