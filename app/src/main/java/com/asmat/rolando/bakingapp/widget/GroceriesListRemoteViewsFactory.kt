package com.asmat.rolando.bakingapp.widget

import android.content.Context
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.asmat.rolando.bakingapp.models.Recipe

/**
 * Created by rolandoasmat on 7/19/17.
 */

class GroceriesListRemoteViewsFactory: RemoteViewsService.RemoteViewsFactory {
    var mContext: Context? = null
    var mRecipe: Recipe? = null

    constructor(context: Context) {
        mContext = context
    }


    override fun onCreate() { }

    override fun getLoadingView(): RemoteViews? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    // Called when factory is created or notify method is called
    override fun onDataSetChanged() {
        // populate mRecipe: Recipe? somehow

    }

    override fun hasStableIds(): Boolean {
        return true
    }

    override fun getViewAt(position: Int): RemoteViews? {
        //val views = RemoteViews(mContext.packageName, )
        return null
    }

    override fun getCount(): Int {
        return mRecipe?.ingredients?.size ?: 0
    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun onDestroy() { }

}