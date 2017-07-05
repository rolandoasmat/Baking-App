package com.asmat.rolando.bakingapp.models

/**
 * Created by rolandoasmat on 7/1/17.
 */

data class Step (
        val id: Int = 0,
        val shortDescription: String = "",
        val description: String = "",
        val videoURL: String = "",
        val thumbnailURL: String = ""
)
