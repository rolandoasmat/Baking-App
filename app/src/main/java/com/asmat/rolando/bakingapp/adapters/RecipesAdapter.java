package com.asmat.rolando.bakingapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.asmat.rolando.bakingapp.R;
import com.asmat.rolando.bakingapp.models.Recipe;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by rolandoasmat on 8/13/17.
 */

public class RecipesAdapter extends ArrayAdapter {

    public View getView(int position, View convertView, ViewGroup parent) {
        View mutableConvertView;
        if(convertView == null) {
            mutableConvertView = LayoutInflater.from(this.getContext()).inflate(R.layout.fragment_recipes_list_item, parent, false);
        } else {
            mutableConvertView = convertView;
        }
        Recipe recipe = (Recipe) this.getItem(position);
        ImageView imageView = (ImageView) mutableConvertView.findViewById(R.id.recipe_image_view);
        TextView textView = (TextView) mutableConvertView.findViewById(R.id.recipe_name_text_view);
        if(!recipe.getImage().equals("")) {
            Picasso.with(getContext()).load(recipe.getImage()).into(imageView);
        }
        textView.setText(recipe.getName());
        return mutableConvertView;
    }

    public RecipesAdapter(Context context, int resource, ArrayList objects) {
        super(context, resource, objects);
    }
}
