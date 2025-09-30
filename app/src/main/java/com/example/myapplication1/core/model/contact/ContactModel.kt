package com.example.myapplication1.core.model.contact

import android.content.Context
import android.net.Uri
import android.provider.ContactsContract
import androidx.core.net.toUri

data class Contact(val name:String, var phoneNumber:String, val profilePic: Uri, val contactId: Int)
object ContactModel {
    var contacts: MutableList<Contact> = mutableListOf()
}


