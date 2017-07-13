package com.asmat.rolando.bakingapp.fragments

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckedTextView
import com.asmat.rolando.bakingapp.R

import com.asmat.rolando.bakingapp.fragments.IngredientsFragment.OnListFragmentInteractionListener
import com.asmat.rolando.bakingapp.models.Ingredient

/**
 * [RecyclerView.Adapter] that can display a [DummyItem] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 * TODO: Replace the implementation with code for your data type.
 */
class IngredientsRecyclerViewAdapter(private val mValues: List<Ingredient>, private val mListener: OnListFragmentInteractionListener?) : RecyclerView.Adapter<IngredientsRecyclerViewAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return mValues.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_ingredient, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mItem = mValues[position]
        holder.checkedTextView.text = mValues[position].ingredientName

        holder.mView.setOnClickListener {
            mListener?.onListFragmentInteraction(holder.mItem!!)
        }
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val checkedTextView: CheckedTextView
        var mItem: Ingredient? = null

        init {
            checkedTextView = mView.findViewById(R.id.checked_text_view_ingredient) as CheckedTextView
        }

    }
}
