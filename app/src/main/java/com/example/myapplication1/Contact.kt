package com.example.myapplication1

import kotlinx.serialization.Serializable

data class Contact(
    val profilePicture: Int,
    var name : String,
    val messageDescription: String
): java.io.Serializable {
}