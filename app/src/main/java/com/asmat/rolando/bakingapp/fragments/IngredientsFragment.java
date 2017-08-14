package com.asmat.rolando.bakingapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.asmat.rolando.bakingapp.R;
import com.asmat.rolando.bakingapp.adapters.IngredientsAdapter;
import com.asmat.rolando.bakingapp.models.Recipe;

/**
 * Created by rolandoasmat on 8/8/17.
 */

public class IngredientsFragment extends Fragment {
    private Recipe mRecipe;
    private IngredientsFragment.OnIngredientsFragmentInteractionListener mListener;
    private RecyclerView mRecyclerView;

    private static final String ARG_RECIPE = "arg_recipe";

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            mRecipe = getArguments().getParcelable(ARG_RECIPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ingredients, container, false);
        Context context = view.getContext();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.setAdapter(new IngredientsAdapter(mRecipe, getActivity(), mListener));
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof IngredientsFragment.OnIngredientsFragmentInteractionListener) {
            mListener = (IngredientsFragment.OnIngredientsFragmentInteractionListener)context;
        } else {
            throw new RuntimeException("must implement OnIngredientsFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnIngredientsFragmentInteractionListener {
        void onIngredientTapped(IngredientsAdapter.ViewHolder var1);
    }

    public static final IngredientsFragment newInstance(Recipe recipe) {
        IngredientsFragment fragment = new IngredientsFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_RECIPE, recipe);
        fragment.setArguments(args);
        return fragment;
    }
}
