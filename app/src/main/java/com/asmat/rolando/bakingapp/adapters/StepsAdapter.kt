package com.asmat.rolando.bakingapp.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.asmat.rolando.bakingapp.R
import com.asmat.rolando.bakingapp.fragments.StepsFragment
import com.asmat.rolando.bakingapp.models.Step

/**
 * Created by rolandoasmat on 7/12/17.
 */

class StepsAdapter(private val mValues: List<Step>, private val mListener: StepsFragment.OnListFragmentInteractionListener?) : RecyclerView.Adapter<StepsAdapter.ViewHolder>() {

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
        holder.mItem = item
        val entry = (position+1).toString() + " " + item.shortDescription
        holder.shortDescriptionTextView.text = entry

        holder.mView.setOnClickListener {
            mListener?.onListFragmentInteraction(holder.mItem!!)
        }
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val shortDescriptionTextView: TextView
        var mItem: Step? = null

        init {
            shortDescriptionTextView = mView.findViewById(R.id.text_view_step_short_description) as TextView
        }
    }
}