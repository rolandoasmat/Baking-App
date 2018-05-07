package com.asmat.rolando.rocoton.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.asmat.rolando.rocoton.R
import com.asmat.rolando.rocoton.models.Recipe
import com.squareup.picasso.Picasso

/**
 * Created by rolandoasmat on 7/4/17.
 */

class RecipesAdapter(context: Context?, resource: Int, objects: ArrayList<out Recipe>?) : ArrayAdapter<Recipe>(context, resource, objects) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val mutableConvertView = convertView ?: LayoutInflater.from(context).inflate(R.layout.fragment_recipes_list_item, parent, false)
        val recipe = getItem(position)
        val imageView = mutableConvertView.findViewById(R.id.recipe_image_view) as ImageView
        val textView = mutableConvertView.findViewById(R.id.recipe_name_text_view) as TextView
        if(!recipe.image.equals("")) {
            Picasso.with(context).load(recipe.image).into(imageView)
        }
        textView.text = recipe.name
        return mutableConvertView
    }
}