package com.example.myapplication1

import java.io.Serializable
import java.sql.Time
import java.time.LocalDate

data class Chats(var sender: Contact?,val phoneNumber:String?, val status:ChatSeenStatus, val timeSent: LocalDate): Serializable{
    val messages: MutableList<Message>? = if(sender!=null && sender?.messages!=null)
                                                    sender?.messages
                                            else mutableListOf<Message>()
}


enum class ChatSeenStatus{
    Seen,
    Delivered,
    Unread
}

//shared transition android fragment  : dialog fragment - view pager2