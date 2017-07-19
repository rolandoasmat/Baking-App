package com.asmat.rolando.bakingapp.adapters

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
    var shouldCheck = ArrayList<IngredientDB>()

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
        val dbItem = IngredientDB(entry)
        if(shouldCheck.contains(dbItem)) {
            IngredientsAdapter.markAsChecked(holder.checkedTextView)
        }
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
    }
}