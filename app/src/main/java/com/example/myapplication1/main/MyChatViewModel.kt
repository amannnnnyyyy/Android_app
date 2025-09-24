package com.example.myapplication1.main

import android.app.Application
import android.net.Uri
import android.provider.ContactsContract
import android.util.Log
import androidx.core.net.toUri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication1.core.model.chat.ChatModel
import com.example.myapplication1.core.model.contact.Contact
import com.example.myapplication1.core.model.contact.ContactModel
import com.example.myapplication1.core.model.message.MessageModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyChatViewModel(application: Application): AndroidViewModel(application) {

    private val _contacts = MutableLiveData<List<Contact>>()
    val contact = _contacts as LiveData<List<Contact>>

    init {
        fetch()
    }

    fun fetch() {
        viewModelScope.launch {
            val contactList = withContext(Dispatchers.IO){
                val context = getApplication<Application>().applicationContext
                val contacts = mutableListOf<Contact>()

                val cursor = context.contentResolver.query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null, null, null, null
                )

                cursor?.use {
                    val nameIndex = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
                    val phoneNumberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                    val photoUriIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI)
                    while (it.moveToNext()) {
                        val name = it.getString(nameIndex)
                        val phoneNumber = it.getString(phoneNumberIndex)
                        val photo = it.getString(photoUriIndex)?.toUri()
                        contacts.add(Contact(0, name, phoneNumber, photo?:Uri.EMPTY))
                    }
                }
                _contacts.postValue(contacts)
            }
        }
    }
}