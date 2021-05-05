package com.uniovi.justbeer.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {
    private val _userProfile = MutableLiveData<FirebaseUser>()
    val userProfile: LiveData<FirebaseUser> = _userProfile

    init {
        viewModelScope.launch {
            _userProfile.value = FirebaseAuth.getInstance().currentUser
        }
    }
}