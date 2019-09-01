package com.asmat.rolando.bakingapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.asmat.rolando.bakingapp.R
import com.asmat.rolando.bakingapp.models.Step

/**
 * Created by rolandoasmat on 7/12/17.
 */

class StepsAdapter(private val mValues: List<Step>) : RecyclerView.Adapter<StepsAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return mValues.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_steps_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        val entry = (position+1).toString() + " " + item.shortDescription
        holder.shortDescriptionTextView.text = entry
    }

    inner class ViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {
        val shortDescriptionTextView: TextView = mView.findViewById(R.id.text_view_step_short_description) as TextView
    }
}