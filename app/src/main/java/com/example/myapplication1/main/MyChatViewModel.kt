package com.example.myapplication1.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication1.core.model.contact.Contact
import com.example.myapplication1.core.model.contact.ContactModel

class MyChatViewModel: ViewModel() {

    private val _contacts: MutableLiveData<List<Contact>> = MutableLiveData()
    val contact = _contacts as LiveData<List<Contact>>

    fun fetch() {
        _contacts.postValue(listOf<Contact>())
    }
}