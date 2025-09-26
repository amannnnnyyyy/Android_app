package com.example.myapplication1.view.fragments.chat_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication1.core.model.chat.Chat
import com.example.myapplication1.core.model.message.Message
import com.example.myapplication1.core.model.message.MessageModel.randomMessages

class ChatDetailViewModel: ViewModel() {
    private val _chatDetails: MutableLiveData<List<Message>> = MutableLiveData<List<Message>>()
    val chatDetails = _chatDetails as LiveData<List<Message>>

    init {

    }

    fun setUpChatDetails(chatList:List<Chat>){
        val messages = mutableListOf<Message>()
        for ((index,chat) in chatList.withIndex()){
            if (index%2==0){
                val msg = randomMessages()
                msg.chatId = chat.id
                chat.hasMessage = true
                messages.add(msg)
            }
        }
        _chatDetails.postValue(messages)
    }
}