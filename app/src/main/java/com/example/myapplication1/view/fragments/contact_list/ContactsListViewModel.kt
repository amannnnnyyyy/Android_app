package com.example.myapplication1.view.fragments.contact_list

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication1.core.model.contact.Contact

class ContactsListViewModel: ViewModel() {
    private val _contacts: MutableLiveData<List<Contact>> = MutableLiveData<List<Contact>>()
    val contacts = _contacts as LiveData<List<Contact>>
    val filtered = MutableLiveData<List<Contact>>()

    private var currentSearchString =  MutableLiveData<String>("")
    fun setUpContacts(contacts: List<Contact>){
        _contacts.postValue(contacts)
    }

    fun getFilteredChats(
        owner: LifecycleOwner,
        searchString: String
    ): List<Contact> {
        var returnedContacts: List<Contact> = listOf<Contact>()
        if (searchString!=currentSearchString.value){
            currentSearchString.value = searchString
            filtered.value = _contacts.value

            filtered.observe(owner) { contacts ->
                returnedContacts = filtered.value.filter{
                    it.name.contains(searchString,ignoreCase = true)
                }


            }
            filtered.postValue(returnedContacts)
        }else {
            returnedContacts = filtered.value
        }
        return returnedContacts
    }
}