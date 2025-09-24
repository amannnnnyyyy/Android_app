package com.example.myapplication1.core.model.contact

import android.content.Context
import android.net.Uri
import android.provider.ContactsContract
import androidx.core.net.toUri

data class Contact(val id:Int,val name:String, val phoneNumber:String, val profilePic: Uri)
object ContactModel {
    var contacts: List<Contact> =emptyList()

//    fun fetchContacts(context: Context): List<Contact> {
//        val contactList = mutableListOf<Contact>()
//
//        val cursor = context.contentResolver.query(
//            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
//            null, null, null, null
//        )
//
//        cursor?.use {
//            val nameIndex = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
//            val phoneNumberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
//            val photoUriIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI)
//            while (it.moveToNext()) {
//                val name = it.getString(nameIndex)
//                val phoneNumber = it.getString(phoneNumberIndex)
//                val photo = it.getString(photoUriIndex)?.toUri()
//                contactList.add(Contact(0, name, phoneNumber, photo?:Uri.EMPTY))
//            }
//        }
//
//        return contactList
//    }
}


