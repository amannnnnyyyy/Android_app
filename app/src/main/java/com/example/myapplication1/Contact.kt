package com.example.myapplication1

import android.net.Uri
import kotlinx.serialization.Serializable

data class Contact(
    val id: Long,
    var messages: MutableList<Message>?,
    val profilePicture: String,
    var name : String,
    val messageDescription: String,
    val phoneNumber: String
): java.io.Serializable {
}