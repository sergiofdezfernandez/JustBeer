package com.uniovi.justbeer.data.database

import android.content.Context
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import com.uniovi.justbeer.model.domain.Review
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await


class ReviewRepository {
    companion object {
        const val COLLECTION = "reviews"
        const val TAG = "ReviewRepository"
    }

    fun getReviewsByBeer(beerId: Long): Query {
        return Firebase.firestore.collection(COLLECTION).whereEqualTo("beerId", beerId)
    }

    fun addReview(review: Review): Boolean {
        return Firebase.firestore.collection(COLLECTION).add(review).isComplete
    }
}