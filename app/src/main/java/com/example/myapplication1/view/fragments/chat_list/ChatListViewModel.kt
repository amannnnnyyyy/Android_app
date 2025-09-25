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
import com.example.myapplication1.core.model.contact.Contact
import com.example.myapplication1.core.model.contact.ContactModel
import com.example.myapplication1.core.model.message.MessageModel
import com.example.myapplication1.core.model.message.ReadStatus

class ChatListViewModel: ViewModel() {
    private val _chats: MutableLiveData<List<Chat>> = MutableLiveData<List<Chat>>()
    val chats = _chats as LiveData<List<Chat>>


    init {
    }


    fun setUpChat(contacts: List<Contact>, registered: Boolean) {
        val chatList = mutableListOf<Chat>()
        for ((index, contact) in contacts.withIndex()) {
            val fav = (index == 4)
            val group = index % 3 == 0
            Log.i("index-inside-chat", "$index ${contact.id}")
            chatList.add(
                Chat(
                    index,
                    sender = contact.id,
                    phoneNumber = if (registered) contact.phoneNumber else null, fav,
                    group,
                    true
                ),
            )
        }
        //_chats.value = chatList
        _chats.postValue(chatList)
    }

    fun getFilteredChats(
        kind: String,
        owner: LifecycleOwner,
        searchString: String? = null
    ): List<Chat> {
        var returnedChats: List<Chat> = listOf<Chat>()
        chats.observe(owner) { chatList ->
            returnedChats = when (kind) {
                "all" -> chatList.filter { it.hasMessage }
                "fav" -> chatList.filter { it.favourite && it.hasMessage }
                "unread" -> chatList.filter { ch ->
                    MessageModel.messagesList.any { msg -> msg.readStatus == ReadStatus.NOT_READ && ch.hasMessage }
                }

                "groups" -> chatList.filter { ch -> ch.group && ch.hasMessage }

                "searching" -> {
                    chatList.filter { chat ->
                        ContactModel.contacts.filter { contact ->
                            contact.name.contains(searchString ?: "", ignoreCase = true)
                        }.any {
                            it.id == chat.sender
                        }
                    }
                }

                else -> emptyList()
            }
        }
       // _chats.postValue(returnedChats)
        return returnedChats
    }
}