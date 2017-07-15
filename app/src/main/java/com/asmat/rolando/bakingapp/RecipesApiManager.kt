package com.asmat.rolando.bakingapp

import android.content.Context
import com.asmat.rolando.bakingapp.Utils.NetworkUtils
import com.asmat.rolando.bakingapp.models.Ingredient
import com.asmat.rolando.bakingapp.models.Recipe
import com.asmat.rolando.bakingapp.models.Step
import org.json.JSONArray
import org.json.JSONObject

/**
 * Created by rolandoasmat on 7/4/17.
 */

class RecipesApiManager {

    companion object {

        val baseUrl = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json"

        fun fetchRecipes(context: Context, onComplete: (Array<Recipe>?) -> Unit) {
            NetworkUtils.httpRequest(baseUrl, context, {response ->
                if(response != null) {
                    val mapped = mapJsonToRecipesArray(response)
                    onComplete(mapped)
                } else {
                    onComplete(null)
                }
            })
        }

        fun mapJsonToRecipesArray(json: String): Array<Recipe> {
            val recipesJson = JSONArray(json)
            val numOfRecipes = recipesJson.length()
            return Array(numOfRecipes, { i -> mapJsonToRecipeObject(recipesJson.getJSONObject(i)) })
        }

        fun mapJsonToRecipeObject(json: JSONObject): Recipe {
            val id = json.getInt("id")
            val name = json.getString("name")
            val ingredients = mapJsonToIngredientsArray(json.getJSONArray("ingredients"))
            val steps = mapJsonToStepsArray(json.getJSONArray("steps"))
            val servings = json.getInt("servings")
            val image = json.getString("image")
            return Recipe(id, name, ingredients, steps, servings, image)
        }

        fun mapJsonToIngredientsArray(jsonArray: JSONArray): Array<Ingredient> {
            val numOfIngredients = jsonArray.length()
            return Array(numOfIngredients, { i -> mapJsonToIngredientObject(jsonArray.getJSONObject(i)) })
        }

        fun mapJsonToIngredientObject(json: JSONObject): Ingredient {
            val quantity = json.getDouble("quantity")
            val measure = json.getString("measure")
            val ingredient = json.getString("ingredient")
            return Ingredient(quantity, measure, ingredient)
        }

        fun mapJsonToStepsArray(jsonArray: JSONArray): Array<Step> {
            val numOfSteps = jsonArray.length()
            return Array(numOfSteps, { i -> mapJsonToStepObject(jsonArray.getJSONObject(i)) })
        }

        fun mapJsonToStepObject(json: JSONObject): Step {
            val id = json.getInt("id")
            val shortDescription = json.getString("shortDescription")
            val description = json.getString("description")
            val videoURL = json.getString("videoURL")
            val thumbnailURL = json.getString("thumbnailURL")
            return Step(id, shortDescription, description, videoURL, thumbnailURL)
        }

    }
}
