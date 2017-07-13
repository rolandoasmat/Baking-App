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
import com.asmat.rolando.bakingapp.adapters.IngredientsRecyclerViewAdapter
import com.asmat.rolando.bakingapp.models.Ingredient

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
class IngredientsFragment : Fragment() {
    private var mItems: Array<Ingredient>? = null
    private var mListener: OnListFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments != null) {
            mItems = arguments.getParcelableArray(ARG_INGREDIENTS) as Array<Ingredient>
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_ingredients_list, container, false)

        // Set the adapter
        val context = view.getContext()
        val recyclerView = view.findViewById(R.id.list) as RecyclerView
        recyclerView.setLayoutManager(LinearLayoutManager(context))
        val list = ArrayList<Ingredient>()
        for(ingredient in mItems!!) {
            list.add(ingredient)
        }
        recyclerView.setAdapter(IngredientsRecyclerViewAdapter(list, mListener))
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
        fun onListFragmentInteraction(item: Ingredient)
    }

    companion object {
        private val ARG_INGREDIENTS = "arg-ingredients"

        fun newInstance(ingredients: Array<Ingredient>): IngredientsFragment {
            val fragment = IngredientsFragment()
            val args = Bundle()
            args.putParcelableArray(ARG_INGREDIENTS, ingredients)
            fragment.arguments = args
            return fragment
        }
    }
}
