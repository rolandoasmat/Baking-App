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
import com.asmat.rolando.bakingapp.Utils.ArrayUtils
import com.asmat.rolando.bakingapp.adapters.IngredientsAdapter
import com.asmat.rolando.bakingapp.models.Ingredient

class IngredientsFragment : Fragment() {
    private var mItems: Array<Ingredient>? = null
    private var mListener: OnIngredientsFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments != null) {
            mItems = arguments.getParcelableArray(ARG_INGREDIENTS) as Array<Ingredient>
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_ingredients, container, false)

        // Set the adapter
        val context = view.context
        val recyclerView = view.findViewById(R.id.list) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        val list = ArrayUtils.toArrayList(mItems!!)
        recyclerView.adapter = IngredientsAdapter(list, mListener)
        return view
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnIngredientsFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    interface OnIngredientsFragmentInteractionListener {
        fun onIngredientTapped(item: Ingredient)
        fun onAddGroceries(view: View)
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