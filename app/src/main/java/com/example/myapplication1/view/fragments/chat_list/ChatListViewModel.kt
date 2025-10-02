package com.example.myapplication1.view.fragments.chat_list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication1.core.model.chat.Chat
import com.example.myapplication1.core.model.chat.ChatModel
import com.example.myapplication1.core.model.contact.ContactModel
import com.example.myapplication1.core.model.message.MessageModel
import com.example.myapplication1.core.model.message.ReadStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ChatListViewModel: ViewModel() {
    private val _chats: LiveData<List<Chat>> = MutableLiveData<List<Chat>>(ChatModel.chats)
    val chats = _chats as MutableLiveData<List<Chat>>
    val filtered = MutableLiveData<List<Chat>>()

    private val _filteredChats = MutableStateFlow<List<Chat>>(mutableListOf<Chat>())
    val filteredChats = _filteredChats.asStateFlow()

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
                "unread" -> chatList.filter { chat ->
                    MessageModel.message.any { msg ->
                        msg.chatId == chat.id && msg.readStatus == ReadStatus.NOT_READ
                    }
                }
                "groups" -> chatList.filter { ch -> ch.group && ch.hasMessage };
                "searching" -> {
                    val query = searchString?.trim()?.lowercase() ?: ""
                    Log.i("searchingWith", "I am searching for: $query")
                    val matchingContactIds = ContactModel.contacts
                        .filter { contact ->
                            contact.name.lowercase().contains(query)
                        }
                        .map { it.contactId }
                        .toSet()

                    val chatsToSearch = _chats.value ?: emptyList()
                    chatsToSearch.filter { chat ->
                        chat.sender in matchingContactIds && chat.hasMessage
                    }
                }


                else -> emptyList()
        }
        filtered.postValue(returnedChats)
        return returnedChats
    }


}