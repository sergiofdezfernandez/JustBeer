package com.uniovi.justbeer.model

import com.uniovi.justbeer.model.api.Beer
import com.uniovi.justbeer.model.domain.Beer as ModelBeer


object ServerDataMapper {
    fun convertToDomain(beer: List<Beer>): List<ModelBeer> = convertBeerListToDomain(beer)

    private fun convertBeerListToDomain(list: List<Beer>): List<ModelBeer> {
        return list.map { beer -> convertBeerItemToDomain(beer) }
    }

    private fun convertBeerItemToDomain(beer: Beer): ModelBeer {
        return ModelBeer(
            id = beer.id,
            name = beer.name,
            image = beer.image_url,
            alcohol = beer.abv,
            description = beer.description
        )
    }
}