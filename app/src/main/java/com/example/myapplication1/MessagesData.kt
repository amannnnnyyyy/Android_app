package com.example.myapplication1

class MessagesData{
    fun getMessageForContact(id:Long,name:String): MutableList<Message> {
        if(name.contains("Abdi")){
            val messagesList = mutableListOf<Message>(
                Message(id,null, null, "How are you?", "received", ReadStatus.READ),
                Message(id,"John Adams", "How are you?", "I am fine. How are you?", "sent",
                    ReadStatus.READ),
                Message(id,"You", "I am fine. How are you?", "I am good.",
                    readStatus =ReadStatus.READ),
                Message(id,null, null, "How was your stay at the hotel?", "received",
                    ReadStatus.READ),
                Message(id,"John Adams", "How was your stay at the hotel?", "It was fine, it ain't much to talk about tho, I've been staying in a 4-start hotel and they're hospitable", "sent",
                    ReadStatus.READ),
                Message(id,"John Adams", "How was your stay at the hotel?", "It was fine, it ain't much to talk about tho, I've been staying in a 4-start hotel and they're hospitable", "received",
                    ReadStatus.UNREAD),
                Message(id,null, null, "It was fine, it ain't much to talk about tho, I've been staying in a 4-start hotel and they're hospitable", "received",
                    ReadStatus.UNREAD)
            )
            return messagesList
        }
        return mutableListOf<Message>()
    }
}
