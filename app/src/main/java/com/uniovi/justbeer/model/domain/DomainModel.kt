package com.uniovi.justbeer.model.domain


import android.os.Parcelable
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.uniovi.justbeer.data.database.ReviewRepository
import com.uniovi.justbeer.data.service.api.PunkServer
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class Beer(
    val id: Long,
    val name: String,
    val image: String?,
    val alcohol: Float,
    val description: String
) : Parcelable

@Parcelize
class Review(
    val username: String,
    val comment: String,
    val score: Int,
    val beerId: Long
) : Parcelable {
    constructor() : this("", "", 0, 0)
}


enum class ProviderType {
    BASIC, GOOGLE
}

@Parcelize
data class UserProfile(
    val id: String?,
    val email: String?,
    val provider: ProviderType
) : Parcelable

data class ReviewList(val reviews: List<Review>) {
    companion object {
        fun requestReviews(beerId: Long): Query {
            return ReviewRepository().getReviewsByBeer(beerId)
        }
    }
}

data class BeerList(val beers: List<Beer>) {
    companion object {
        suspend fun requestBeers(): List<Beer> {
            return PunkServer().requestBeers()
        }
        suspend fun requestRecommendation(): Beer{
            return PunkServer().requestRecommendation()
        }
    }
}