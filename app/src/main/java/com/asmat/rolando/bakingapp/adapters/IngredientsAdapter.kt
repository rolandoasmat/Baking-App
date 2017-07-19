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

class IngredientsAdapter(private val mValues: List<Ingredient>, private val mListener: OnIngredientsFragmentInteractionListener?) : RecyclerView.Adapter<IngredientsAdapter.ViewHolder>() {
    var mContext: Context? = null

    override fun getItemCount(): Int {
        return mValues.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_ingredients_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.ingredient = item
        val entry = item.quantity.toString().replace(".0", "") + " " + item.measure + " of " + item.ingredientName
        holder.checkedTextView.text = entry
        holder.mView.setOnClickListener {
            mListener?.onIngredientTapped(holder)
        }
        checkIngredientsFromDB(holder)
    }

    fun checkIngredientsFromDB(holder: ViewHolder) {
        val db = AppDatabase.getInstance(mContext!!)
        object : AsyncTask<Void, Void, List<IngredientDB>>() {
            override fun doInBackground(vararg params: Void): List<IngredientDB> {
                if(db == null) { return ArrayList() }
                return db.getAllIngredients()
            }

            override fun onPostExecute(result: List<IngredientDB>) {
                for(ingredient in result) {
                    if(ingredient.ingredient == holder.checkedTextView.text) {
                        markAsChecked(holder.checkedTextView)
                    }
                }
            }
        }.execute()
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        var ingredient: Ingredient? = null
        val checkedTextView: CheckedTextView = mView.findViewById(R.id.checked_text_view_ingredient) as CheckedTextView
    }

    companion object {
        fun markAsChecked(checkedTextView: CheckedTextView) {
            checkedTextView.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            checkedTextView.isEnabled = false
            checkedTextView.setCheckMarkDrawable(R.drawable.check_24dp)
        }

        fun markAsUnChecked(checkedTextView: CheckedTextView, context: Context) {
            checkedTextView.paintFlags = 0
            checkedTextView.isEnabled = true
            checkedTextView.setCheckMarkDrawable(R.drawable.check_box_outline_24dp)
            val db = AppDatabase.getInstance(context)
            object : AsyncTask<Void, Void, Int>() {
                override fun doInBackground(vararg params: Void): Int {
                    if(db == null) { return -1}
                    val ingredientDB = db.findByName(checkedTextView.text.toString())
                    db.delete(ingredientDB)
                    return 0
                }
            }.execute()
        }
    }
}