package com.uniovi.justbeer.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class User(val userId: String, val email: String, val imageUrl: String) : Parcelable