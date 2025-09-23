package com.example.myapplication1.data.model.chat

import com.example.myapplication1.data.model.contact.Contact

data class Chat(val id:Int, var sender: Int, val phoneNumber:String?=null, val favourite:Boolean, val group:Boolean){

}

object ChatModel {
    var chats: MutableList<Chat> = mutableListOf()


    fun setUpChat(contacts:List<Contact>, registered:Boolean){
        for ((index,contact) in contacts.withIndex()){
            val fav = index==0
            val group = index%3==0
            chats.add(Chat(
                index,
                sender = contact.id,
                phoneNumber = if(registered) contact.phoneNumber else null
            ,fav,
                group),)
        }
    }
}