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
    private val _chats: LiveData<List<Chat>> = MutableLiveData<List<Chat>>(ChatModel.chats)
    val chats = _chats as MutableLiveData<List<Chat>>
    val filtered = MutableLiveData<List<Chat>>()

    private val _choiceToDisplayChats = MutableLiveData("all")
    val choiceToDisplayChats = _choiceToDisplayChats as LiveData<String>

    fun changeDisplayedChatType(type:String){
        _choiceToDisplayChats.postValue(type)
    }
    init {
        chats.postValue(ChatModel.chats)
    }

    fun getFilteredChats(
        kind: String,
        searchString: String? = null
    ): List<Chat> {
        filtered.value = _chats.value
        var returnedChats: List<Chat> = listOf<Chat>()
        val chatList = filtered.value
            returnedChats = when (kind) {
                "all" -> chatList.filter { it.hasMessage }
                "fav" -> chatList.filter { it.favourite && it.hasMessage }
                "unread" -> chatList.filter { ch -> MessageModel.message.any { msg -> msg.readStatus == ReadStatus.NOT_READ && ch.hasMessage } }
                "groups" -> chatList.filter { ch -> ch.group && ch.hasMessage };
                "searching" -> {
                    Log.i("searchingWith","I am searching for: $searchString\n\t found: ${filtered.value.size}")
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
        filtered.postValue(returnedChats)
        return returnedChats
    }
}