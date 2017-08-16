package com.asmat.rolando.bakingapp.adapters;

import android.content.Context;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

import com.asmat.rolando.bakingapp.R;
import com.asmat.rolando.bakingapp.db.AppDatabase;
import com.asmat.rolando.bakingapp.db.ShoppingListDao;
import com.asmat.rolando.bakingapp.db.ShoppingListIngredient;
import com.asmat.rolando.bakingapp.fragments.IngredientsFragment;
import com.asmat.rolando.bakingapp.models.Ingredient;
import com.asmat.rolando.bakingapp.models.Recipe;


/**
 * Created by rolandoasmat on 8/7/17.
 */

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder> {
    
    private final Recipe mRecipe;
    private final Context mContext;
    private final IngredientsFragment.OnIngredientsFragmentInteractionListener mListener;

    public IngredientsAdapter(Recipe recipe, Context context, IngredientsFragment.OnIngredientsFragmentInteractionListener listener) {
        mRecipe = recipe;
        mContext = context;
        mListener = listener;
    }

    @Override
    public int getItemCount() {
        return mRecipe.getIngredients().length;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_ingredients_list_item, parent, false);
        return new IngredientsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final IngredientsAdapter.ViewHolder holder, int position) {
        Ingredient item = mRecipe.getIngredients()[position];
        holder.setIngredient(item);
        holder.setRecipe(mRecipe);
        holder.getCheckedTextView().setText(item.createListEntry());
        holder.getMView().setOnClickListener((new View.OnClickListener() {
            public final void onClick(View it) {
                mListener.onIngredientTapped(holder);
            }
        }));
        checkIngredient(holder);
    }

    public final void checkIngredient(final IngredientsAdapter.ViewHolder holder) {
        final ShoppingListDao db = AppDatabase.getInstance(mContext);
        (new AsyncTask<Void, Void, ShoppingListIngredient>() {

            @Override
            public ShoppingListIngredient doInBackground(Void... params) {
                if(db == null) {
                    return new ShoppingListIngredient();
                } else {
                    return db.findIngredient(holder.getRecipe().getName(), holder.getIngredient().createListEntry());
                }
            }

            @Override
            public void onPostExecute(ShoppingListIngredient result) {
                if(result.getNeeded()) {
                    IngredientsAdapter.markAsUnChecked(holder.getCheckedTextView());
                } else {
                    IngredientsAdapter.markAsChecked(holder.getCheckedTextView());
                }
            }
        }).execute();
    }

    public final class ViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder {

        private Recipe recipe;
        private Ingredient ingredient;
        private final CheckedTextView checkedTextView;
        private final View mView;

        public final Recipe getRecipe() {
            return this.recipe;
        }

        public final void setRecipe(Recipe recipe) {
            this.recipe = recipe;
        }


        public final Ingredient getIngredient() {
            return this.ingredient;
        }

        public final void setIngredient(Ingredient ingredient) {
            this.ingredient = ingredient;
        }

        public final CheckedTextView getCheckedTextView() {
            return this.checkedTextView;
        }

        
        public final View getMView() {
            return this.mView;
        }

        public ViewHolder(View view) {
            super(view);
            mView = view;
            checkedTextView = mView.findViewById(R.id.checked_text_view_ingredient);
        }
    }

    public static void markAsChecked( CheckedTextView checkedTextView) {
        checkedTextView.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        checkedTextView.setEnabled(false);
        checkedTextView.setCheckMarkDrawable(R.drawable.check_24dp);
    }

    public static void markAsUnChecked( CheckedTextView checkedTextView) {
        checkedTextView.setPaintFlags(0);
        checkedTextView.setEnabled(true);
        checkedTextView.setCheckMarkDrawable(R.drawable.check_box_outline_24dp);
    }

    public static void addToDatabase(final String recipeName, final String ingredientEntry, final boolean needed, Context context) {
        final ShoppingListDao db = AppDatabase.getInstance(context);
        (new AsyncTask<Void, Void, Integer>() {
            @Override
            public Integer doInBackground( Void... params) {
                if(db == null) {
                    return -1;
                } else {
                    ShoppingListIngredient ingredientOld = db.findIngredient(recipeName, ingredientEntry);
                    db.delete(ingredientOld);
                    ShoppingListIngredient shoppingListIngredient = new ShoppingListIngredient(ingredientEntry);
                    shoppingListIngredient.setRecipe(recipeName);
                    shoppingListIngredient.setNeeded(needed);
                    db.insert(shoppingListIngredient);
                    return 0;
                }
            }
        }).execute();
    }
}