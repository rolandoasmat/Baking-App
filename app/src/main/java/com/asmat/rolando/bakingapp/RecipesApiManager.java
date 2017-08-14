package com.asmat.rolando.bakingapp;

import android.content.Context;

import com.asmat.rolando.bakingapp.Utils.ArrayUtils;
import com.asmat.rolando.bakingapp.Utils.NetworkUtils;
import com.asmat.rolando.bakingapp.models.CompletionClosure;
import com.asmat.rolando.bakingapp.models.Ingredient;
import com.asmat.rolando.bakingapp.models.Recipe;
import com.asmat.rolando.bakingapp.models.Step;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by rolandoasmat on 8/8/17.
 */

public class RecipesApiManager {

    private static final String baseUrl = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";


    public static final void fetchRecipes(Context context, final CompletionClosure<ArrayList<Recipe>> closure) {
        NetworkUtils.httpRequest(baseUrl, context, (new CompletionClosure<String>() {
            @Override
            public void onComplete(String response) {
                if (response != null) {
                    Recipe[] mapped = RecipesApiManager.mapJsonToRecipesArray(response);
                    closure.onComplete(ArrayUtils.toArrayList(mapped));
                } else {
                    closure.onComplete(null);
                }
            }
        }));
    }

    public static Recipe[] mapJsonToRecipesArray(String json) {
        try {
            JSONArray recipesJson = new JSONArray(json);
            int numOfRecipes = recipesJson.length();
            Recipe[] recipes = new Recipe[numOfRecipes];
            for(int i = 0; i < numOfRecipes; i++) {
                recipes[i] = mapJsonToRecipeObject(recipesJson.getJSONObject(i));
            }
            return recipes;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Recipe mapJsonToRecipeObject(JSONObject json) {
        try {
            int id = json.getInt("id");
            String name = json.getString("name");
            Ingredient[] ingredients = mapJsonToIngredientsArray(json.getJSONArray("ingredients"));
            Step[] steps = mapJsonToStepsArray(json.getJSONArray("steps"));
            int servings = json.getInt("servings");
            String image = json.getString("image");
            return new Recipe(id, name, ingredients, steps, servings, image);
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Ingredient[] mapJsonToIngredientsArray(JSONArray jsonArray) {
        try {
            int numOfIngredients = jsonArray.length();
            Ingredient[] ingredients = new Ingredient[numOfIngredients];
            for(int i = 0; i < numOfIngredients; i++) {
                ingredients[i] = mapJsonToIngredientObject(jsonArray.getJSONObject(i));
            }
            return ingredients;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Ingredient mapJsonToIngredientObject(JSONObject json) {
        try {
            double quantity = json.getDouble("quantity");
            String measure = json.getString("measure");
            String ingredient = json.getString("ingredient");
            return new Ingredient(quantity, measure, ingredient);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Step[] mapJsonToStepsArray(JSONArray jsonArray) {
        try {
            int numOfSteps = jsonArray.length();
            Step[] steps = new Step[numOfSteps];
            for(int i = 0; i < numOfSteps; i++) {
                steps[i] = mapJsonToStepObject(jsonArray.getJSONObject(i));
            }
            return steps;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Step mapJsonToStepObject(JSONObject json) {
        try {
            int id = json.getInt("id");
            String shortDescription = json.getString("shortDescription");
            String description = json.getString("description");
            String videoURL = json.getString("videoURL");
            String thumbnailURL = json.getString("thumbnailURL");
            return new Step(id, shortDescription, description, videoURL, thumbnailURL);
        } catch (Exception e) {
            return null;
        }
    }
}