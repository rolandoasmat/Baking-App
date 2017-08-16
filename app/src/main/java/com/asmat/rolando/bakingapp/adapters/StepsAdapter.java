package com.asmat.rolando.bakingapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.asmat.rolando.bakingapp.R;
import com.asmat.rolando.bakingapp.models.Step;

import java.util.List;

/**
 * Created by rolandoasmat on 8/14/17.
 */

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.ViewHolder> {
    private final List<Step> mValues;

    public StepsAdapter(List<Step> values) {
        this.mValues = values;
    }

    @Override
    public int getItemCount() {
        return this.mValues.size();
    }

    @Override
    public StepsAdapter.ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_steps_list_item, parent, false);
        return new StepsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StepsAdapter.ViewHolder holder, int position) {
        Step item = mValues.get(position);
        String entry = position + 1 + " " + item.getShortDescription();
        holder.getShortDescriptionTextView().setText(entry);
    }

    public final class ViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder {
        
        private final TextView shortDescriptionTextView;

        
        public final TextView getShortDescriptionTextView() {
            return this.shortDescriptionTextView;
        }

        public ViewHolder( View mView) {
            super(mView);
            shortDescriptionTextView = mView.findViewById(R.id.text_view_step_short_description);
        }
    }
}
