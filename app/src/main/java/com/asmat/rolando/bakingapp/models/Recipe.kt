package com.asmat.rolando.bakingapp.models

/**
 * Created by rolandoasmat on 7/1/17.
 */
data class Recipe (
        val id: Int,
        val name: String,
        val ingredients: Array<com.asmat.rolando.bakingapp.models.Ingredient>,
        val steps: Array<Step>,
        val servings: Int,
        val image: String
)