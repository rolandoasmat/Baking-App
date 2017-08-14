package com.asmat.rolando.bakingapp.fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.asmat.rolando.bakingapp.R;
import com.asmat.rolando.bakingapp.RecipesApiManager;
import com.asmat.rolando.bakingapp.Utils.ArrayUtils;
import com.asmat.rolando.bakingapp.activities.MainActivity;
import com.asmat.rolando.bakingapp.adapters.RecipesAdapter;
import com.asmat.rolando.bakingapp.db.AppDatabase;
import com.asmat.rolando.bakingapp.db.ShoppingListDao;
import com.asmat.rolando.bakingapp.db.ShoppingListIngredient;
import com.asmat.rolando.bakingapp.models.CompletionClosure;
import com.asmat.rolando.bakingapp.models.Ingredient;
import com.asmat.rolando.bakingapp.models.Recipe;

import java.util.ArrayList;
import java.util.Collection;

import java.util.List;

/**
 * Created by rolandoasmat on 8/8/17.
 */

public class RecipesListFragment extends ListFragment implements AdapterView.OnItemClickListener {
    
    private RecipesListFragment.OnRecipeClickListener callback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recipes, container, false);
    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setListAdapter((new RecipesAdapter(getActivity(), R.layout.fragment_recipes_list_item, new ArrayList())));
        getListView().setOnItemClickListener(this);
        fetchRecipes();
    }

    @Override
    public void onAttach( Context context) {
        super.onAttach(context);
        if(context instanceof RecipesListFragment.OnRecipeClickListener) {
            this.callback = (RecipesListFragment.OnRecipeClickListener)context;
        } else {
            throw new RuntimeException("Activity does not implement OnRecipeClickListener");
        }
    }

    @Override
    public void onItemClick( AdapterView parent,  View view, int position, long id) {
        if(callback != null) {
            Recipe recipe = (Recipe) ((RecipesAdapter) getListAdapter()).getItem(position);
            callback.onRecipeSelected(recipe);
        }
    }

    private final void fetchRecipes() {
        RecipesApiManager.fetchRecipes( getActivity(), (new CompletionClosure<ArrayList<Recipe>>() {

            @Override
            public void onComplete(ArrayList<Recipe> response) {

                Recipe[] array = new Recipe[response.size()];
                for(int i = 0; i < response.size(); i++) {
                    array[i] = response.get(i);
                }

                RecipesListFragment.this.updateDB(array);

                RecipesAdapter recipesAdapter = (RecipesAdapter) RecipesListFragment.this.getListAdapter();

                if(response != null) {

                    recipesAdapter.addAll(response);

                    if(getContext() instanceof MainActivity) {
                        MainActivity mainActivity = (MainActivity) getContext();
                        if(mainActivity.mIsDualPane) {
                            RecipesListFragment.this.callback.onRecipeSelected(response.get(0));
                        }
                    }
                } else {
                    recipesAdapter.clear();
                }
            }
        }));
    }

    private final void updateDB(final Recipe[] recipes) {
        if(recipes != null) {
            final ShoppingListDao db = AppDatabase.getInstance(this.getContext());
            (new AsyncTask<Void, Void, List<ShoppingListIngredient>>() {

                @Override
                public List doInBackground(Void... params) {
                    return db.getAllIngredients();
                }

                @Override
                public void onPostExecute(List<ShoppingListIngredient> result) {
                    if(result.isEmpty()) {
                        populateDB(recipes);
                    } else {
                        System.out.print("DB already populated");
                    }

                }
            }).execute();
        }
    }

    private final void populateDB(final Recipe[] recipes) {
        if(recipes != null) {
            final ShoppingListDao db = AppDatabase.getInstance(getContext());
            (new AsyncTask<Void, Void, Integer>() {

                @Override
                public Integer doInBackground(Void... params) {
                    if(db == null) {
                        return -1;
                    } else {
                        for(Recipe recipe: recipes) {
                            for(Ingredient ingredient: recipe.getIngredients()) {
                                ShoppingListIngredient shoppingListIngredient = new ShoppingListIngredient(ingredient.createListEntry());
                                shoppingListIngredient.setRecipe(recipe.getName());
                                shoppingListIngredient.setNeeded(true);
                                db.insert(shoppingListIngredient);
                            }
                        }
                        return 0;
                    }
                }
            }).execute();
        }
    }

    public interface OnRecipeClickListener {
        void onRecipeSelected(Recipe var1);
    }
}
