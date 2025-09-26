package com.example.myapplication1.view.fragments.chat_list

import android.app.Fragment
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication1.SharedViewModel
import com.example.myapplication1.core.model.chat.Chat
import com.example.myapplication1.core.model.chat.ChatModel
import com.example.myapplication1.core.model.contact.Contact
import com.example.myapplication1.core.model.contact.ContactModel
import com.example.myapplication1.core.model.message.MessageModel
import com.example.myapplication1.core.model.message.ReadStatus

class ChatListViewModel: ViewModel() {
    private val _chats: MutableLiveData<List<Chat>> = MutableLiveData<List<Chat>>()
    val chats = _chats as LiveData<List<Chat>>
    val filtered = MutableLiveData<List<Chat>>()


    init {
    }


    fun setUpChat(contacts: List<Contact>, registered: Boolean) {
        val chatList = mutableListOf<Chat>()
        val contactsList = mutableListOf<Contact>()
        for ((index, contact) in contacts.withIndex()) {
            val fav = (index == 4)
            val group = index % 3 == 0
            Log.i("index-inside-chat", "$index ${contact.contactId}")
            if (index%8==0){
                chatList.add(
                    Chat(
                        index,
                        sender = contact.contactId,
                        phoneNumber = if (registered) contact.phoneNumber else null, fav,
                        group,
                        true
                    ),
                )
                contactsList.add(contact)
            }
        }
        //_chats.value = chatList
        //ChatModel.setUpChat(contactsList,false)
        MessageModel.setUpMessages(chatList)
        _chats.postValue(chatList)
    }

    fun getFilteredChats(
        kind: String,
        owner: LifecycleOwner,
        searchString: String? = null
    ): List<Chat> {
        filtered.value = _chats.value
        Log.i("searching_now","with $searchString")
        var returnedChats: List<Chat> = listOf<Chat>()
        filtered.observe(owner) { chatList ->
            returnedChats = when (kind) {
                "all" -> chatList.filter { it.hasMessage }
                "fav" -> {
                    val list = _chats.value.filter { it.favourite && it.hasMessage }
                    filtered.postValue(chatList);
                    list
                }
                "unread" -> chatList.filter { ch ->
                    val list = MessageModel.messagesList.any { msg -> msg.readStatus == ReadStatus.NOT_READ && ch.hasMessage }
                    filtered.postValue(chatList);
                    list
                }

                "groups" -> {
                    val list = chatList.filter { ch -> ch.group && ch.hasMessage };
                    filtered.postValue(chatList);
                    list
                }

                "searching" -> {
                    val list = if(filtered.value.isNotEmpty()) filtered.value else chatList
                    list.filter { chat ->
                        ContactModel.contacts.filter { contact ->
                            contact.name.contains(searchString ?: "", ignoreCase = true)
                        }.any {
                            it.contactId == chat.sender
                        }
                    }
                }

                else -> emptyList()
            }
        }
        filtered.postValue(returnedChats)
        Log.i("searching_now","${_chats.value?.size}")
        return returnedChats
    }
}