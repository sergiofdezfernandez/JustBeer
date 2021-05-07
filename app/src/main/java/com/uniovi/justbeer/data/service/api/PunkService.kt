package com.uniovi.justbeer.data.service.api


import com.uniovi.justbeer.model.api.Beer
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PunkService {

    @GET("beers")
    fun requestBeers(): Call<List<Beer>>
}