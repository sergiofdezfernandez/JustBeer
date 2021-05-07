package com.uniovi.justbeer.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uniovi.justbeer.model.domain.Beer
import com.uniovi.justbeer.model.domain.BeerList
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val _beerList: MutableLiveData<List<Beer>> by lazy {
        MutableLiveData<List<Beer>>()
    }
    val beerList: LiveData<List<Beer>>
        get() = _beerList


    fun requestBeers() {
        viewModelScope.launch {
            _beerList.value = BeerList.requestBeers()
        }

    }
}