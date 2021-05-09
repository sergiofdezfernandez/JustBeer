package com.uniovi.justbeer.model.domain


import android.os.Parcelable
import com.uniovi.justbeer.data.service.api.PunkServer
import kotlinx.parcelize.Parcelize

@Parcelize
data class Beer(val id: Long, val name: String, val image: String?, val alcohol: Float) : Parcelable

data class BeerList(val beers: List<Beer>){
    companion object {
        suspend fun requestBeers(): List<Beer> {
            return PunkServer().requestBeers()
        }
        suspend fun requestRecommendation(): Beer{
            return PunkServer().requestRecommendation()
        }
    }
}