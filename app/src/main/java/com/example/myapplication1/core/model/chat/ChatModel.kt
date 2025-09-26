package com.example.myapplication1.core.model.chat

import android.util.Log
import com.example.myapplication1.core.model.contact.Contact

data class Chat(val id:Int, var sender: Int, val phoneNumber:String?=null, val favourite:Boolean, val group:Boolean, var hasMessage: Boolean){

}

object ChatModel {
    var chats: MutableList<Chat> = mutableListOf()


    fun setUpChat(contacts:List<Contact>, registered:Boolean){
        for ((index,contact) in contacts.withIndex()){
            val fav = (index==4)
            val group = index%3==0
            chats.add(Chat(
                index,
                sender = contact.contactId,
                phoneNumber = if(registered) contact.phoneNumber else null
            ,fav,
                group,
                false),)
        }
    }
}