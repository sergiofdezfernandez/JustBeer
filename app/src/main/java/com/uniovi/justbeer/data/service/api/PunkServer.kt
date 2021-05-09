package com.uniovi.justbeer.data.service.api

import com.uniovi.justbeer.model.ServerDataMapper
import com.uniovi.justbeer.model.domain.Beer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.await
import retrofit2.converter.gson.GsonConverterFactory

class PunkServer{
    companion object{
        private const val API_BASE_URL = "https://api.punkapi.com/v2/"
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl(API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val serviceBeer = retrofit.create(PunkService::class.java)

    suspend fun requestBeers(): List<Beer>{
        return withContext(Dispatchers.IO){
            val result = serviceBeer.requestBeers().await()
            ServerDataMapper.convertToDomain(result)
        }
    }

    suspend fun requestRecommendation(): Beer{
        return withContext(Dispatchers.IO){
            val result = serviceBeer.requestRecommendation().await()
            ServerDataMapper.convertToDomain(result)[0]
        }
    }
}