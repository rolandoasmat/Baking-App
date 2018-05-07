package com.asmat.rolando.rocoton.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import com.asmat.rolando.rocoton.R
import android.content.Intent

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in [GroceriesListWidgetConfigureActivity]
 */
class GroceriesListWidget : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onDeleted(context: Context, appWidgetIds: IntArray) {
        // When the user deletes the widget, delete the preference associated with it.
        for (appWidgetId in appWidgetIds) {
            GroceriesListWidgetConfigureActivity.deleteTitlePref(context, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    companion object {
        val INTENT_EXTRA_RECIPE_TITLE = "recipe_title"

        internal fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
            val recipeTitle = GroceriesListWidgetConfigureActivity.loadTitlePref(context, appWidgetId)
            // Construct the RemoteViews object
            val views = RemoteViews(context.packageName, R.layout.groceries_list_widget)
            val intent = Intent(context, GridWidgetService::class.java)
            intent.putExtra(INTENT_EXTRA_RECIPE_TITLE, recipeTitle)
            views.setRemoteAdapter(R.id.widget_grid_view, intent)
            views.setTextViewText(R.id.widget_title_text_view, recipeTitle)
            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}

