package com.example.myapplication1.view.fragments.contact_detail

import android.app.Application
import android.database.Cursor
import android.net.Uri
import android.provider.ContactsContract.CommonDataKinds
import android.util.Log
import androidx.core.database.getStringOrNull
import androidx.core.net.toUri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication1.core.model.contact.Contact


class ContactDetailsViewModel(application: Application): AndroidViewModel(application) {
   private val _contactDetails: MutableLiveData<Contact> = MutableLiveData<Contact>()
    val contactDetails = _contactDetails as LiveData<Contact>

    fun fetchContactDetail(id: Int) {
        Log.d("MyChatViewModel", "fetch - $this")
        val context = getApplication<Application>().applicationContext
        var contactSingle : Contact? = null

        val cursor: Cursor? = context.contentResolver.query(
            CommonDataKinds.Phone.CONTENT_URI,
            null,
            CommonDataKinds.Phone.CONTACT_ID + " = ?",
            arrayOf<String?>(id.toString()), null
        )
        cursor?.use {
            val phoneNumberIndex = it.getColumnIndex(CommonDataKinds.Phone.NUMBER)
            val nameIndex = it.getColumnIndex(CommonDataKinds.Phone.DISPLAY_NAME)
            val photoUriIndex = cursor.getColumnIndex(CommonDataKinds.Phone.PHOTO_URI)

            if (cursor.moveToNext()) {
                val phoneNumber = it.getStringOrNull(phoneNumberIndex)
                val name = it.getStringOrNull(nameIndex)
                val photoUri = it.getStringOrNull(photoUriIndex)?.toUri()?: Uri.EMPTY
                contactSingle = Contact(
                    name?:"Unknown",
                    phoneNumber?:"Unknown",
                    profilePic = photoUri,
                    contactId = id
                )
            }
        }

        cursor?.close()
        contactSingle.let{_contactDetails.postValue(it)}
    }

}