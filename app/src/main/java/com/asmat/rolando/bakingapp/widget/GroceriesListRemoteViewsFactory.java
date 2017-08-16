package com.asmat.rolando.bakingapp.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.asmat.rolando.bakingapp.R;
import com.asmat.rolando.bakingapp.db.AppDatabase;
import com.asmat.rolando.bakingapp.db.ShoppingListDao;
import com.asmat.rolando.bakingapp.db.ShoppingListIngredient;

import java.util.List;


/**
 * Created by rolandoasmat on 8/15/17.
 */

public class GroceriesListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context mContext;
    private List<ShoppingListIngredient> mIngredients;
    private String mRecipeTitle;

    public GroceriesListRemoteViewsFactory(Context context, String recipeTitle) {
        this.mRecipeTitle = "";
        this.mContext = context;
        this.mRecipeTitle = recipeTitle;
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {
        if(this.mContext != null) {
            ShoppingListDao db = AppDatabase.getInstance(mContext);
            mIngredients = db.getAllIngredientsOfRecipe(this.mRecipeTitle);
        }
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public int getCount() {
        return this.mIngredients != null?this.mIngredients.size():0;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if(mIngredients == null) {
            return null;
        } else {
            String ingredientEntry = mIngredients.get(position).getIngredient();
            RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.groceries_list_widget_item);
            views.setTextViewText(R.id.ingredient_entry, ingredientEntry);
            return views;
        }
    }

    public RemoteViews getLoadingView() {
        return null;
    }

    public int getViewTypeCount() {
        return 1;
    }

    public long getItemId(int position) {
        return (long)position;
    }

    public boolean hasStableIds() {
        return true;
    }

    public final Context getMContext() {
        return this.mContext;
    }

    public final void setMContext(Context context) {
        this.mContext = context;
    }

    
    public final List getMIngredients() {
        return this.mIngredients;
    }

    public final void setMIngredients(List<ShoppingListIngredient> ingredients) {
        this.mIngredients = ingredients;
    }

    
    public final String getMRecipeTitle() {
        return this.mRecipeTitle;
    }

    public final void setMRecipeTitle(String title) {
        this.mRecipeTitle = title;
    }
}

class GridWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        String recipeTitle = intent.getStringExtra(GroceriesListWidget.INTENT_EXTRA_RECIPE_TITLE);
        return (new GroceriesListRemoteViewsFactory(getApplicationContext(), recipeTitle));
    }
}
