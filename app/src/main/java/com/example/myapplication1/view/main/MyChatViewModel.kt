package com.example.myapplication1.view.main

import android.app.Application
import android.net.Uri
import android.provider.ContactsContract
import android.util.Log
import androidx.core.net.toUri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication1.core.model.chat.ChatModel
import com.example.myapplication1.core.model.contact.Contact
import com.example.myapplication1.core.model.contact.ContactModel
import com.example.myapplication1.core.model.message.MessageModel

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
                val visitedNameList = mutableSetOf<String>()

                val cursor = context.contentResolver.query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null, null, null, null
                )

                cursor?.use {
                    var i = 0
                    val idIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID)
                    val nameIndex = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
                    val phoneNumberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                    val photoUriIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI)
                    while (it.moveToNext()) {
                        val id = it.getString(idIndex)
                        val name = it.getString(nameIndex)
                        val phoneNumber = it.getString(phoneNumberIndex)
                        val photo = it.getString(photoUriIndex)?.toUri()
                        val contact = Contact(i, name, phoneNumber, photo?:Uri.EMPTY, id)
                        if (!visitedNameList.contains(id)){
                            visitedNameList.add(id)
                            contacts.add(contact)
                            ContactModel.contacts.add(contact)
                        }else{
                            val cont = contacts.find { con->
                                con.contactId == id }
                                //if (cont?.phoneNumber!= phoneNumber)
                                    cont?.phoneNumber += phoneNumber
                        }
                        Log.i("check_duplicates","$name   $id")
                        i++
                    }
                }
                _contacts.postValue(contacts)
                ChatModel.setUpChat(contacts, false)
                MessageModel.setUpMessages()
//            }
//        }
    }
}