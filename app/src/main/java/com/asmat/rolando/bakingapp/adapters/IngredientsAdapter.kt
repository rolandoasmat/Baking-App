package com.asmat.rolando.bakingapp.adapters

import android.content.Context
import android.graphics.Paint
import android.os.AsyncTask
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckedTextView
import com.asmat.rolando.bakingapp.R
import com.asmat.rolando.bakingapp.db.AppDatabase
import com.asmat.rolando.bakingapp.db.IngredientDB

import com.asmat.rolando.bakingapp.fragments.IngredientsFragment.OnIngredientsFragmentInteractionListener
import com.asmat.rolando.bakingapp.models.Ingredient
import com.asmat.rolando.bakingapp.models.Recipe

class IngredientsAdapter(private val mRecipe: Recipe,
                         private val mContext: Context,
                         private val mListener: OnIngredientsFragmentInteractionListener?) : RecyclerView.Adapter<IngredientsAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return mRecipe.ingredients.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_ingredients_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mRecipe.ingredients[position]
        holder.ingredient = item
        holder.recipe = mRecipe
        holder.checkedTextView.text = item.createListEntry()
        holder.mView.setOnClickListener {
            mListener?.onIngredientTapped(holder)
        }
        checkIngredientsFromDB(holder)
    }

    fun checkIngredientsFromDB(holder: ViewHolder) {
        val db = AppDatabase.getInstance(mContext)
        object : AsyncTask<Void, Void, List<IngredientDB>>() {
            override fun doInBackground(vararg params: Void): List<IngredientDB> {
                if(db == null) { return ArrayList() }
                return db.getAllIngredients()
            }

            override fun onPostExecute(result: List<IngredientDB>) {
                for(ingredient in result) {
                    if(ingredient.ingredient == mRecipe.name + holder.checkedTextView.text) {
                        markAsChecked(holder)
                        return
                    }
                }
            }
        }.execute()
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        var recipe: Recipe? = null
        var ingredient: Ingredient? = null
        val checkedTextView: CheckedTextView = mView.findViewById(R.id.checked_text_view_ingredient) as CheckedTextView
    }

    companion object {
        fun markAsChecked(viewHolder: IngredientsAdapter.ViewHolder) {
            val checkedTextView = viewHolder.checkedTextView
            checkedTextView.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            checkedTextView.isEnabled = false
            checkedTextView.setCheckMarkDrawable(R.drawable.check_24dp)
        }

        fun addToDatabase(viewHolder: IngredientsAdapter.ViewHolder, recipe: Recipe, context: Context) {
            val ingredient = viewHolder.ingredient!!
            val entry = recipe.name + ingredient.createListEntry()
            val ingredientDB = IngredientDB(entry)
            val db = AppDatabase.getInstance(context)
            object : AsyncTask<Void, Void, Int>() {
                override fun doInBackground(vararg params: Void): Int {
                    db?.insert(ingredientDB)
                    return 0
                }
            }.execute()
        }

        fun markAsUnChecked(checkedTextView: CheckedTextView) {
            checkedTextView.paintFlags = 0
            checkedTextView.isEnabled = true
            checkedTextView.setCheckMarkDrawable(R.drawable.check_box_outline_24dp)
        }

        fun removeFromDatabase(checkedTextView: CheckedTextView, recipe: Recipe, context: Context) {
            val db = AppDatabase.getInstance(context)
            object : AsyncTask<Void, Void, Int>() {
                override fun doInBackground(vararg params: Void): Int {
                    if(db == null) { return -1}
                    val ingredientDB = db.findByName(recipe.name + checkedTextView.text.toString())
                    db.delete(ingredientDB)
                    return 0
                }
            }.execute()
        }
    }
}