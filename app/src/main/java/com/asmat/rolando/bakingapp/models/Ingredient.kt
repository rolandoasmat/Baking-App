package com.asmat.rolando.bakingapp.models

/**
 * Created by rolandoasmat on 7/1/17.
 */
data class Ingredient (
        val quantity: Int = 0,
        val measure: Int = Measure.UNIT,
        val ingredientName: String = ""
)