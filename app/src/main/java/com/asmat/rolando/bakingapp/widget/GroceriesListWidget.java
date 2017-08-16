package com.asmat.rolando.bakingapp.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.asmat.rolando.bakingapp.R;

/**
 * Created by rolandoasmat on 8/15/17.
 */

public class GroceriesListWidget extends AppWidgetProvider {
    public static final String INTENT_EXTRA_RECIPE_TITLE = "recipe_title";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for(int id: appWidgetIds) {
            updateAppWidget(context, appWidgetManager, id);
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        for(int id: appWidgetIds) {
            GroceriesListWidgetConfigureActivity.deleteTitlePref(context, id);
        }
    }

    @Override
    public void onEnabled( Context context) {
    }

    @Override
    public void onDisabled( Context context) {
    }

    public final static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        String recipeTitle = GroceriesListWidgetConfigureActivity.loadTitlePref(context, appWidgetId);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.groceries_list_widget);
        Intent intent = new Intent(context, GridWidgetService.class);
        intent.putExtra(INTENT_EXTRA_RECIPE_TITLE, recipeTitle);
        views.setRemoteAdapter(R.id.widget_grid_view, intent);
        views.setTextViewText(R.id.widget_title_text_view, recipeTitle);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
}
