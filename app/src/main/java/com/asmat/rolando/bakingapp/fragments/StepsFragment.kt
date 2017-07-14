package com.asmat.rolando.bakingapp.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.asmat.rolando.bakingapp.R
import com.asmat.rolando.bakingapp.adapters.StepsAdapter
import com.asmat.rolando.bakingapp.models.Step

/**
 * Created by rolandoasmat on 7/12/17.
 */

class StepsFragment : Fragment() {
    private var mItems: Array<Step>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mItems = arguments.getParcelableArray(ARG_STEPS) as Array<Step>
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_steps, container, false)

        val context = view.context
        val recyclerView = view.findViewById(R.id.list) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        val list = ArrayList<Step>()
        for(step in mItems!!) {
            list.add(step)
        }
        recyclerView.adapter = StepsAdapter(list)
        return view
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context !is OnStepsFragmentInteractionListener) {
            throw RuntimeException(context!!.toString() + " must implement OnStepsFragmentInteractionListener")
        }
    }

    interface OnStepsFragmentInteractionListener {
        fun onBeginRecipe(view:View)
    }

    companion object {
        private val ARG_STEPS= "arg_steps"

        fun newInstance(steps: Array<Step>): StepsFragment {
            val fragment = StepsFragment()
            val args = Bundle()
            args.putParcelableArray(ARG_STEPS, steps)
            fragment.arguments = args
            return fragment
        }
    }
}
