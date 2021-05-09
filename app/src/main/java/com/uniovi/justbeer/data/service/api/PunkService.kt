package com.uniovi.justbeer.data.service.api


import com.uniovi.justbeer.model.api.Beer
import retrofit2.Call
import retrofit2.http.GET

interface PunkService {

    @GET("beers")
    fun requestBeers(): Call<List<Beer>>

    @GET("beers/random")
    fun requestRecommendation(): Call<List<Beer>>
}