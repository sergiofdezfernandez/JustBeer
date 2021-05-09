package com.uniovi.justbeer.ui.activities.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uniovi.justbeer.data.database.ReviewRepository
import com.uniovi.justbeer.model.domain.Review

class ReviewsViewModel : ViewModel() {
    private val _review: MutableLiveData<Review> by lazy {
        MutableLiveData<Review>()
    }
    val review: LiveData<Review>
        get() = _review
    fun requestAddReview(review:Review): Boolean {
        return ReviewRepository().addReview(review)
    }
}