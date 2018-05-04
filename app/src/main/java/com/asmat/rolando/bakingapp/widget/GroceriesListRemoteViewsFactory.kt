package com.asmat.rolando.bakingapp.widget

import android.content.Context
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import android.content.Intent
import com.asmat.rolando.bakingapp.R
import com.asmat.rolando.bakingapp.db.AppDatabase
import com.asmat.rolando.bakingapp.db.ShoppingListIngredient


/**
 * Created by rolandoasmat on 7/19/17.
 */

class GridWidgetService : RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent): RemoteViewsService.RemoteViewsFactory {
        // Get recipe name from intent
        val recipeTitle = intent.getStringExtra(GroceriesListWidget.INTENT_EXTRA_RECIPE_TITLE)
        return GroceriesListRemoteViewsFactory(this.applicationContext, recipeTitle)
    }
}

class GroceriesListRemoteViewsFactory: RemoteViewsService.RemoteViewsFactory {
    var mContext: Context? = null
    var mIngredients: List<ShoppingListIngredient>? = null
    var mRecipeTitle = ""

    constructor(context: Context, recipeTitle: String) {
        mContext = context
        mRecipeTitle = recipeTitle
    }

    override fun onCreate() { }

    //called on start and when notifyAppWidgetViewDataChanged is called
    override fun onDataSetChanged() {
        // populate mRecipe: Recipe? somehow
        if(mContext != null) {
            val db = AppDatabase.getInstance(mContext!!)
            mIngredients = db?.getAllIngredientsOfRecipe(mRecipeTitle)
        }
    }

    override fun onDestroy() { }

    override fun getCount(): Int {
        return mIngredients?.size ?: 0
    }

    override fun getViewAt(position: Int): RemoteViews? {
        if(mIngredients == null) { return null }
        val ingredientEntry = mIngredients?.get(position)?.ingredient
        val views = RemoteViews(mContext?.packageName, R.layout.groceries_list_widget_item)
        views.setTextViewText(R.id.ingredient_entry, ingredientEntry)
        return views
    }

    override fun getLoadingView(): RemoteViews? {
        return null
    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun hasStableIds(): Boolean {
        return true
    }
}