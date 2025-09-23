package com.example.myapplication1.core.model.message

import com.example.myapplication1.core.model.chat.ChatModel


data class Message(var chatId: Int, val repliedTo:String?, val originalMessage:String?, val message:String, val type: MessageType= MessageType.RECEIVED, var readStatus:ReadStatus){

}
enum class ReadStatus{
    READ,
    NOT_READ
}


enum class MessageType{
    SENT,
    RECEIVED
}

object MessageModel {
    var message: MutableList<Message> = mutableListOf()



    fun setUpMessages(){
        val chatList = ChatModel.chats
        for ((index,chat) in chatList.withIndex()){
            if (index%2==0){
                val msg = randomMessages()
                msg.chatId = chat.id
                chat.hasMessage = true
                message.add(msg)
            }
        }
    }

    val messagesList = mutableListOf<Message>(
        Message(0,null, null, "How are you?", MessageType.RECEIVED, ReadStatus.READ),
        Message(0,"John Adams", "How are you?", "I am fine. How are you?", MessageType.SENT,
            ReadStatus.READ),
        Message(0,"You", "I am fine. How are you?", "I am good.",
            readStatus = ReadStatus.READ),
        Message(0,null, null, "How was your stay at the hotel?", MessageType.RECEIVED,
            ReadStatus.READ),
        Message(0,"John Adams", "How was your stay at the hotel?", "It was fine, it ain't much to talk about tho, I've been staying in a 4-start hotel and they're hospitable", MessageType.RECEIVED,
            ReadStatus.READ),
        Message(0,"John Adams", "How was your stay at the hotel?", "It was fine, it ain't much to talk about tho, I've been staying in a 4-start hotel and they're hospitable", MessageType.RECEIVED,
            ReadStatus.READ),
        Message(0,null, null, "It was fine, it ain't much to talk about tho, I've been staying in a 4-start hotel and they're hospitable", MessageType.RECEIVED,
            ReadStatus.READ)
    )


    fun randomMessages():Message{
            val random = (0..(messagesList.size-1)).random()
            return messagesList[random]
    }
}