package com.example.myapplication1.view.fragments.chat_detail

import android.app.Application
import android.database.Cursor
import android.net.Uri
import android.provider.ContactsContract.CommonDataKinds
import android.util.Log
import androidx.core.database.getStringOrNull
import androidx.core.net.toUri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication1.core.model.chat.Chat
import com.example.myapplication1.core.model.contact.Contact
import com.example.myapplication1.core.model.message.Message
import com.example.myapplication1.core.model.message.MessageType
import com.example.myapplication1.core.model.message.ReadStatus

class ChatDetailViewModel(application: Application): AndroidViewModel(application) {
    private val _chatDetails: MutableLiveData<List<Message>> = MutableLiveData<List<Message>>()
    val chatDetails = _chatDetails as LiveData<List<Message>>

    val _sender : MutableLiveData<Contact> = MutableLiveData<Contact>()
    val sender = _sender as LiveData<Contact>

    init {

    }

    fun setUpChatDetails(chatList:List<Chat>){
        val messages = mutableListOf<Message>()
        for ((index,chat) in chatList.withIndex()){
            //if (index==0){
                val msg = randomMessages()
                msg.chatId = chat.id
                chat.hasMessage = true
                messages.add(msg)
           // }else chat.hasMessage = false
        }
        _chatDetails.postValue(messages)
    }

    fun randomMessages():Message{
        val random = (0..(messagesList.size-1)).random()
        return messagesList[random]
    }

    val messagesList = mutableListOf<Message>(
        Message(0,null, null, "How are you?", MessageType.RECEIVED, ReadStatus.READ),
        Message(0,"John Adams", "How are you?", "I am fine. How are you?", MessageType.SENT,
            ReadStatus.NOT_READ),
        Message(0,"You", "I am fine. How are you?", "I am good.",
            readStatus = ReadStatus.NOT_READ),
        Message(0,null, null, "How was your stay at the hotel?", MessageType.RECEIVED,
            ReadStatus.NOT_READ),
        Message(0,"John Adams", "How was your stay at the hotel?", "It was fine, it ain't much to talk about tho, I've been staying in a 4-start hotel and they're hospitable", MessageType.RECEIVED,
            ReadStatus.NOT_READ),
        Message(0,"John Adams", "How was your stay at the hotel?", "It was fine, it ain't much to talk about tho, I've been staying in a 4-start hotel and they're hospitable", MessageType.RECEIVED,
            ReadStatus.NOT_READ),
        Message(0,null, null, "It was fine, it ain't much to talk about tho, I've been staying in a 4-start hotel and they're hospitable", MessageType.RECEIVED,
            ReadStatus.NOT_READ)
    )

    fun fetchSender(id: Int) {
        Log.d("MyChatViewModel", "fetch - $this")
        val context = getApplication<Application>().applicationContext
        var contactSingle : Contact? = null

        val cursor: Cursor? = context.contentResolver.query(
            CommonDataKinds.Phone.CONTENT_URI,
            null,
            CommonDataKinds.Phone.CONTACT_ID + " = ?",
            arrayOf<String?>(id.toString()), null
        )
        cursor?.use {
            val phoneNumberIndex = it.getColumnIndex(CommonDataKinds.Phone.NUMBER)
            val nameIndex = it.getColumnIndex(CommonDataKinds.Phone.DISPLAY_NAME)
            val photoUriIndex = cursor.getColumnIndex(CommonDataKinds.Phone.PHOTO_URI)

            if (cursor.moveToNext()) {
                val phoneNumber = it.getStringOrNull(phoneNumberIndex)
                val name = it.getStringOrNull(nameIndex)
                val photoUri = it.getStringOrNull(photoUriIndex)?.toUri()?: Uri.EMPTY
                contactSingle = Contact(
                    name?:"Unknown",
                    phoneNumber?:"Unknown",
                    profilePic = photoUri,
                    contactId = id
                )
            }
        }

        cursor?.close()
        contactSingle.let{_sender.postValue(it)}
    }
}