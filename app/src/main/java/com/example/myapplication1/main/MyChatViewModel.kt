package com.example.myapplication1.main

import android.app.Application
import android.net.Uri
import android.provider.ContactsContract
import androidx.core.net.toUri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication1.core.model.contact.Contact
import com.example.myapplication1.core.model.contact.ContactModel

class MyChatViewModel(application: Application): AndroidViewModel(application) {

    private val _contacts = MutableLiveData<List<Contact>>()
    val contact = _contacts as LiveData<List<Contact>>

    init {
        fetch()
    }

    fun fetch() {
//        viewModelScope.launch {
//            val contactList = withContext(Dispatchers.IO){
                val context = getApplication<Application>().applicationContext
                val contacts = mutableListOf<Contact>()

                val cursor = context.contentResolver.query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null, null, null, null
                )

                cursor?.use {
                    var i = 0
                    val nameIndex = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
                    val phoneNumberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                    val photoUriIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI)
                    while (it.moveToNext()) {
                        val name = it.getString(nameIndex)
                        val phoneNumber = it.getString(phoneNumberIndex)
                        val photo = it.getString(photoUriIndex)?.toUri()
                        val contact = Contact(i, name, phoneNumber, photo?:Uri.EMPTY)
                        contacts.add(contact)
                        ContactModel.contacts.add(contact)
                        i++
                    }
                }
                _contacts.postValue(contacts)
//            }
//        }
    }
}