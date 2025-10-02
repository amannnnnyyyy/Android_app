package com.example.myapplication1.core.model.chat

import android.util.Log
import com.example.myapplication1.core.model.message.MessageModel

data class Chat(val id:Int?=null, var sender: Int?=null, val phoneNumber:String?=null, val favourite:Boolean?=null, val group:Boolean?=null, var hasMessage: Boolean?=null, val type:DisplayType){

}


enum class DisplayType{
    ADVERTISEMENT, NORMAL_CHAT
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
                hasMessage = !flagHasNoMessage,
                type = DisplayType.NORMAL_CHAT
            )
        )

        val random = (0..5).random()
        if (random%5==0){
            chats.add(
                Chat(
                    type = DisplayType.ADVERTISEMENT
                )
            )
        }
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