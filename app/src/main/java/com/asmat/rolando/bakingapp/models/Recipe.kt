package com.asmat.rolando.bakingapp.models

/**
 * Created by rolandoasmat on 7/1/17.
 */
data class Recipe (
        val id: Int = 0,
        val name: String = "",
        val ingredients: Array<Ingredient> = Array(0) { Ingredient()},
        val steps: Array<Step> = Array(0) { Step() },
        val servings: Int = 0,
        val image: String = ""
)