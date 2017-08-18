package com.asmat.rolando.bakingapp.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by rolandoasmat on 8/17/17.
 */

class GridWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        String recipeTitle = intent.getStringExtra(GroceriesListWidget.INTENT_EXTRA_RECIPE_TITLE);
        return (new GroceriesListRemoteViewsFactory(getApplicationContext(), recipeTitle));
    }
}