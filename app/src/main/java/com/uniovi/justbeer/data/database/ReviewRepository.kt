package com.uniovi.justbeer.data.database


import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.uniovi.justbeer.model.domain.Review


class ReviewRepository {
    companion object {
        const val COLLECTION = "reviews"
    }

    fun getReviewsByBeer(beerId: Long): Query {
        return Firebase.firestore.collection(COLLECTION).whereEqualTo("beerId", beerId)
    }

    fun addReview(review: Review): Boolean {
        return Firebase.firestore.collection(COLLECTION).add(review).isComplete
    }
}