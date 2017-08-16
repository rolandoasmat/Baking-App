package com.asmat.rolando.bakingapp.widget;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.asmat.rolando.bakingapp.R;
import com.asmat.rolando.bakingapp.fragments.RecipesListFragment;
import com.asmat.rolando.bakingapp.models.Recipe;

/**
 * Created by rolandoasmat on 8/15/17.
 */

public class GroceriesListWidgetConfigureActivity extends AppCompatActivity implements RecipesListFragment.OnRecipeClickListener {
    private int mAppWidgetId;
    public static final String PREFS_NAME = "com.asmat.rolando.bakingapp.widget.GroceriesListWidget";
    public static final String PREF_PREFIX_KEY = "appwidget_";

    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        this.setResult(Activity.RESULT_CANCELED);
        this.setContentView(R.layout.groceries_list_widget_configure);
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            this.mAppWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        if(this.mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
        }
    }

    @Override
    public void onRecipeSelected( Recipe recipe) {
        Context context = getApplicationContext();
        saveTitlePref(context, mAppWidgetId, recipe.getName());

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        GroceriesListWidget.updateAppWidget(context, appWidgetManager, mAppWidgetId);


        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
        setResult(Activity.RESULT_OK, resultValue);
        finish();
    }

    public static final void saveTitlePref( Context context, int appWidgetId,  String text) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.putString(PREF_PREFIX_KEY + appWidgetId, text);
        prefs.apply();
    }


    public static String loadTitlePref( Context context, int appWidgetId) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        String titleValue = prefs.getString(PREF_PREFIX_KEY + appWidgetId, null);
        if(titleValue != null) {
            return titleValue;
        } else {
            return context.getString(R.string.appwidget_text);
        }
    }

    public static void deleteTitlePref( Context context, int appWidgetId) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.remove(PREF_PREFIX_KEY + appWidgetId);
        prefs.apply();
    }
}
