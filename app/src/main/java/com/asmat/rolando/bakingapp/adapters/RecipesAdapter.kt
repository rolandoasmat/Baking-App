package com.asmat.rolando.bakingapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.asmat.rolando.bakingapp.R
import com.asmat.rolando.bakingapp.models.Recipe
import com.squareup.picasso.Picasso

/**
 * Created by rolandoasmat on 7/4/17.
 */

class RecipesAdapter(context: Context?, resource: Int, objects: ArrayList<out Recipe>?) : ArrayAdapter<Recipe>(context, resource, objects) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var convertView = convertView
        val recipe = getItem(position)
        if(convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.recipe_list_item, parent, false)
        }
        val imageView = convertView?.findViewById(R.id.recipe_image_view) as ImageView
        val textView = convertView?.findViewById(R.id.recipe_name_text_view) as TextView

        if(recipe.image != "") {
            Picasso.with(context).load(recipe.image).into(imageView)
        }
        textView.text = recipe.name

        return convertView!!
    }

}