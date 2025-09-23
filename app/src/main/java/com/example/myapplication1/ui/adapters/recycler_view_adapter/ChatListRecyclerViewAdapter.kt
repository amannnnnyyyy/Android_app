package com.example.myapplication1.ui.adapters.recycler_view_adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication1.R
import com.example.myapplication1.data.model.chat.Chat

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
        holder.itemView.let{

        }
    }

    override fun getItemCount(): Int = chats.size

    inner class ChatListRecyclerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
}