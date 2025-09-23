package com.example.myapplication1.ui.adapters.recycler_view_adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication1.R
import com.example.myapplication1.data.model.chat.Chat
import com.example.myapplication1.data.model.contact.ContactModel
import com.example.myapplication1.data.model.message.Message
import com.example.myapplication1.data.model.message.MessageModel
import com.google.android.material.imageview.ShapeableImageView

class ChatListRecyclerViewAdapter(chatList:List<Chat>): RecyclerView.Adapter<ChatListRecyclerViewAdapter.ChatListRecyclerViewHolder>() {
    val chats = chatList

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ChatListRecyclerViewHolder {
        return ChatListRecyclerViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.chats,parent,false))
    }

    override fun onBindViewHolder(
        holder: ChatListRecyclerViewHolder,
        position: Int
    ) {
        val chat = chats[position]
        val profilePic = holder.itemView.findViewById<ShapeableImageView>(R.id.userProfile)
        val name = holder.itemView.findViewById<TextView>(R.id.name)
        val message = holder.itemView.findViewById<TextView>(R.id.message)

        val contact = ContactModel.contacts.find {
            it.id == chat.sender
        }
        val lastMessage: String? = MessageModel.messagesList.find { it.chatId == chat.id }?.message

        holder.itemView.let{
            profilePic.setImageURI(contact?.profilePic)
            name.text = contact?.name
            message.text = lastMessage
        }
    }

    override fun getItemCount(): Int = chats.size

    inner class ChatListRecyclerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
}