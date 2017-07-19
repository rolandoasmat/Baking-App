package com.asmat.rolando.bakingapp.fragments

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckedTextView

import com.asmat.rolando.bakingapp.R
import com.asmat.rolando.bakingapp.Utils.ArrayUtils
import com.asmat.rolando.bakingapp.adapters.IngredientsAdapter
import com.asmat.rolando.bakingapp.db.AppDatabase
import com.asmat.rolando.bakingapp.db.IngredientDB
import com.asmat.rolando.bakingapp.models.Ingredient

class IngredientsFragment : Fragment() {
    private var mItems: Array<Ingredient>? = null
    private var mListener: OnIngredientsFragmentInteractionListener? = null
    var mRecyclerView: RecyclerView? = null

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
        mRecyclerView = view.findViewById(R.id.list) as RecyclerView
        mRecyclerView?.layoutManager = LinearLayoutManager(context)
        val list = ArrayUtils.toArrayList(mItems!!)
        mRecyclerView?.adapter = IngredientsAdapter(list, mListener)
        fetchIngredientsFromDB()
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

    // TODO use this fetch and check text views somewhere else?
    fun fetchIngredientsFromDB() {
        val db = AppDatabase.getInstance(context)
        object : AsyncTask<Void, Void, List<IngredientDB>>() {
            override fun doInBackground(vararg params: Void): List<IngredientDB> {
                if(db == null) { return ArrayList() }
                return db.getAllIngredients()
            }

            override fun onPostExecute(result: List<IngredientDB>) {
                for(ingredient in result) {
                    (mRecyclerView?.adapter as IngredientsAdapter).shouldCheck.add(ingredient)
                    (mRecyclerView?.adapter as IngredientsAdapter).notifyDataSetChanged()
                }
            }
        }.execute()
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    interface OnIngredientsFragmentInteractionListener {
        fun onIngredientTapped(item: IngredientsAdapter.ViewHolder)
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