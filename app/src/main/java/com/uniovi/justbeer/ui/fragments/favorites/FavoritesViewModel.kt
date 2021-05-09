package com.uniovi.justbeer.ui.fragments.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uniovi.justbeer.model.domain.Beer
import com.uniovi.justbeer.model.domain.BeerList
import kotlinx.coroutines.launch

class FavoritesViewModel : ViewModel() {
    private val _recomendation: MutableLiveData<Beer> by lazy {
        MutableLiveData<Beer>()
    }
    val recommendation: LiveData<Beer>
        get() = _recomendation


    fun requestRecomendation() {
        viewModelScope.launch {
            _recomendation.value = BeerList.requestRecommendation()
        }

    }
}