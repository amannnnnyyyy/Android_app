package com.example.myapplication1.view.adapters.recycler_view_adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication1.R
import com.example.myapplication1.core.model.chat.Chat
import com.example.myapplication1.core.model.contact.ContactModel
import com.example.myapplication1.core.model.message.MessageModel
import com.example.myapplication1.core.model.message.ReadStatus
import com.google.android.material.imageview.ShapeableImageView

class ChatListRecyclerViewAdapter(chatList:List<Chat>): RecyclerView.Adapter<ChatListRecyclerViewAdapter.ChatListRecyclerViewHolder>() {
    val chats = chatList

    var onClickListener: OnItemClickListener? = null

    fun interface OnItemClickListener{
        fun onClick(chatId:Int)
    }

    fun setClickListener(listener: OnItemClickListener){
        this.onClickListener = listener
    }

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
        val searchField = holder.itemView.findViewById<LinearLayout>(R.id.search)
        if (position == 0){
            searchField.visibility = View.VISIBLE
        }else{
            searchField.visibility = View.GONE
        }
        val chat = chats[position]
        val profilePic = holder.itemView.findViewById<ShapeableImageView>(R.id.userProfile)
        val name = holder.itemView.findViewById<TextView>(R.id.name)
        val message = holder.itemView.findViewById<TextView>(R.id.message)
        val notificationIconNumber = holder.itemView.findViewById<TextView>(R.id.notification)
        val chatsView = holder.itemView.findViewById<RelativeLayout>(R.id.chats)

        val contact = ContactModel.contacts.find {
            it.contactId == chat.sender
        }
        val lastMessage: String? = MessageModel.messagesList.find { it.chatId == chat.id }?.message

        holder.itemView.let{
            profilePic.setImageURI(contact?.profilePic)
            name.text = contact?.name
            message.text = lastMessage
        }

        chatsView.setOnClickListener {
            onClickListener?.onClick(chat.id)
        }

        val unreadNumber = MessageModel.messagesList.filter {
            (it.chatId == chat.id) && (it.readStatus == ReadStatus.NOT_READ) }.size

        if(unreadNumber>0)
            notificationIconNumber.text = unreadNumber.toString()
        else
            notificationIconNumber.visibility = View.GONE
    }

    override fun getItemCount(): Int = chats.size

    inner class ChatListRecyclerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
}