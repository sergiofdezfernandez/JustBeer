package com.uniovi.justbeer.model.api

data class Beer(
    val id: Long,
    val name: String,
    val image_url: String?,
    val abv: Float,
    val description: String
)
