package com.example.myapplication1

import java.io.Serializable

data class Message(val contactId:Long?,val repliedTo:String?, val originalMessage:String?, val message:String, val type:String="received", var readStatus:ReadStatus): Serializable

enum class ReadStatus{
    READ,
    UNREAD,
}