package com.asmat.rolando.bakingapp.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckedTextView
import com.asmat.rolando.bakingapp.R

import com.asmat.rolando.bakingapp.fragments.IngredientsFragment.OnIngredientsFragmentInteractionListener
import com.asmat.rolando.bakingapp.models.Ingredient

class IngredientsAdapter(private val mValues: List<Ingredient>, private val mListener: OnIngredientsFragmentInteractionListener?) : RecyclerView.Adapter<IngredientsAdapter.ViewHolder>() {

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
        val entry = item.quantity.toString().replace(".0", "") + " " + item.measure + " of " + item.ingredientName
        holder.checkedTextView.text = entry

        holder.mView.setOnClickListener {
            mListener?.onIngredientTapped(item)
        }
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val checkedTextView: CheckedTextView = mView.findViewById(R.id.checked_text_view_ingredient) as CheckedTextView
    }
}