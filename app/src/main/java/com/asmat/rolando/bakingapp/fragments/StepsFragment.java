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
import com.asmat.rolando.bakingapp.adapters.StepsAdapter;
import com.asmat.rolando.bakingapp.models.Step;

import java.util.ArrayList;

/**
 * Created by rolandoasmat on 8/15/17.
 */

public class StepsFragment extends Fragment {
    private Step[] mItems;
    private static final String ARG_STEPS = "arg_steps";

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            mItems = (Step[]) getArguments().getParcelableArray(ARG_STEPS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_steps, container, false);
        Context context = view.getContext();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list);
        recyclerView.setLayoutManager((new LinearLayoutManager(context)));
        ArrayList<Step> list = new ArrayList();
        for(Step step: mItems) {
            list.add(step);
        }
        recyclerView.setAdapter(new StepsAdapter(list));
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(!(context instanceof StepsFragment.OnStepsFragmentInteractionListener)) {
            throw new RuntimeException(context.toString() + " must implement OnStepsFragmentInteractionListener");
        }
    }

    public interface OnStepsFragmentInteractionListener {
        void onBeginRecipe(View view);
    }

    public final static StepsFragment newInstance(Step[] steps) {
        StepsFragment fragment = new StepsFragment();
        Bundle args = new Bundle();
        args.putParcelableArray(ARG_STEPS, steps);
        fragment.setArguments(args);
        return fragment;
    }
}