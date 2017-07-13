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

/**
 * A fragment representing a list of Items.
 *
 *
 * Activities containing this fragment MUST implement the [OnListFragmentInteractionListener]
 * interface.
 */
/**
 * Mandatory empty constructor for the fragment manager to instantiate the
 * fragment (e.g. upon screen orientation changes).
 */
class StepsFragment : Fragment() {
    private var mItems: Array<Step>? = null
    private var mListener: OnListFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments != null) {
            mItems = arguments.getParcelableArray(ARG_STEPS) as Array<Step>
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_steps, container, false)

        // Set the adapter
        val context = view.getContext()
        val recyclerView = view.findViewById(R.id.list) as RecyclerView
        recyclerView.setLayoutManager(LinearLayoutManager(context))
        val list = ArrayList<Step>()
        for(step in mItems!!) {
            list.add(step)
        }
        recyclerView.setAdapter(StepsAdapter(list, mListener))
        return view
    }


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
            mListener = context as OnListFragmentInteractionListener?
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
    interface OnListFragmentInteractionListener {
        fun onListFragmentInteraction(item: Step)
    }

    companion object {
        private val ARG_STEPS= "arg-steps"

        fun newInstance(steps: Array<Step>): StepsFragment {
            val fragment = StepsFragment()
            val args = Bundle()
            args.putParcelableArray(ARG_STEPS, steps)
            fragment.arguments = args
            return fragment
        }
    }
}
