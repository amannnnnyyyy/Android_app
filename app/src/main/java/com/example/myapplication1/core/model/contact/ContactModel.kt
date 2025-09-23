package com.example.myapplication1.core.model.contact

import android.content.Context
import android.net.Uri
import android.provider.ContactsContract

data class Contact(val id:Int,val name:String, val phoneNumber:String, val profilePic: Uri)
object ContactModel {
    var contacts: List<Contact> =emptyList()

    fun fetchContacts(context: Context): List<String> {
        val contactList = mutableListOf<String>()

        val cursor = context.contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            null, null, null, null
        )

        cursor?.use {
            val nameIndex = it.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
            while (it.moveToNext()) {
                val name = it.getString(nameIndex)
                contactList.add(name)
            }
        }

        return contactList
    }
}


