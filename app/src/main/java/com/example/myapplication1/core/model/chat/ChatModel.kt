package com.example.myapplication1.core.model.chat

import android.util.Log
import com.example.myapplication1.core.model.message.MessageModel

data class Chat(val id:Int, var sender: Int, val phoneNumber:String?=null, val favourite:Boolean, val group:Boolean, var hasMessage: Boolean){

}

object ChatModel {
    var chats: MutableList<Chat> = mutableListOf()


    fun setUpChat(contactId:Int, id:Int, flagHasNoMessage:Boolean=false){
        MessageModel.setUpMessages(id)
        Log.i("setupTheChat","inside chat   $id \n\t${MessageModel.message}")

        chats.add(
            Chat(
                id = id,
                sender = contactId,
                phoneNumber = null,
                favourite = randomBoolean(),
                group = randomBoolean(),
                hasMessage = !flagHasNoMessage
            )
        )
    }

    fun randomBoolean(): Boolean{
        val random = (0..1).random()
        return when(random){
            0 -> false
            1 -> true
            else -> false
        }
    }
}