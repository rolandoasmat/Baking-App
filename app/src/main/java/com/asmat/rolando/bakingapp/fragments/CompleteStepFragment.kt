package com.asmat.rolando.bakingapp.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.asmat.rolando.bakingapp.R
import com.asmat.rolando.bakingapp.models.Recipe

class CompleteStepFragment : Fragment() {

    private var mRecipe: Recipe? = null
    private var mPosition: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mRecipe = arguments.getParcelable(ARG_RECIPE)
            mPosition = arguments.getInt(ARG_POSITION)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_complete_step, container, false)
    }

    override fun onDetach() {
        super.onDetach()
    }

    companion object {
        private val ARG_RECIPE = "arg_recipe"
        private val ARG_POSITION = "arg_position"

        fun newInstance(recipe: Recipe, position: Int): CompleteStepFragment {
            val fragment = CompleteStepFragment()
            val args = Bundle()
            args.putParcelable(ARG_RECIPE, recipe)
            args.putInt(ARG_POSITION, position)
            fragment.arguments = args
            return fragment
        }
    }
}