package com.uniovi.justbeer.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uniovi.justbeer.data.database.ReviewRepository
import com.uniovi.justbeer.model.domain.Review
import kotlinx.coroutines.launch

class ReviewsViewModel : ViewModel() {
    private val _reviewList: MutableLiveData<List<Review>> by lazy {
        MutableLiveData<List<Review>>()
    }
    val reviewList: LiveData<List<Review>>
        get() = _reviewList

    private val _review: MutableLiveData<Review> by lazy {
        MutableLiveData<Review>()
    }
    val review: LiveData<Review>
        get() = _review


    fun requestReviewByUsername(username: String) {
        viewModelScope.launch {

        }
    }
    fun requestAddReview(review:Review): Boolean {
        return ReviewRepository().addReview(review)
    }
}