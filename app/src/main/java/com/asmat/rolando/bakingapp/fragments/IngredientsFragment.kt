package com.asmat.rolando.bakingapp.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.asmat.rolando.bakingapp.R
import com.asmat.rolando.bakingapp.adapters.IngredientsAdapter
import com.asmat.rolando.bakingapp.models.Recipe

class IngredientsFragment : Fragment() {
    private var mRecipe: Recipe? = null
    private var mListener: OnIngredientsFragmentInteractionListener? = null
    private var mRecyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val arguments = this.arguments
        if (arguments != null) {
            mRecipe = arguments.getParcelable(ARG_RECIPE)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_ingredients, container, false)
        val context = view.context
        mRecyclerView = view.findViewById(R.id.list) as RecyclerView
        mRecyclerView?.layoutManager = LinearLayoutManager(context)
        mRecyclerView?.adapter = IngredientsAdapter(mRecipe!!, context, mListener)
        return view
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnIngredientsFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnIngredientsFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    interface OnIngredientsFragmentInteractionListener {
        fun onIngredientTapped(item: IngredientsAdapter.ViewHolder)
    }

    companion object {
        private val ARG_RECIPE = "arg_recipe"

        fun newInstance(recipe: Recipe): IngredientsFragment {
            val fragment = IngredientsFragment()
            val args = Bundle()
            args.putParcelable(ARG_RECIPE, recipe)
            fragment.arguments = args
            return fragment
        }
    }
}