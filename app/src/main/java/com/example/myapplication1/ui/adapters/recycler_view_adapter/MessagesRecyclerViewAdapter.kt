package com.example.myapplication1.ui.adapters.recycler_view_adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication1.R
import com.example.myapplication1.core.model.message.Message

class MessagesRecyclerViewAdapter(val messagesList: List<Message>)
    : RecyclerView.Adapter<MessagesRecyclerViewAdapter.MessagesViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MessagesViewHolder {
        return MessagesViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.messages_layout, parent, false))
    }

    override fun onBindViewHolder(
        holder: MessagesViewHolder,
        position: Int
    ) {
        val msg  = messagesList[position]

        holder.messageBubble2.visibility = View.GONE
        holder.reply.visibility = View.GONE

        holder.username.text = msg.repliedTo
        holder.sentMessage.text = msg.message

    }

    override fun getItemCount(): Int = messagesList.size

    inner class MessagesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val messageBubble = itemView.findViewById<ConstraintLayout>(R.id.message_bubble)
        val messageBubble2 = itemView.findViewById<ConstraintLayout>(R.id.message_bubble2)
        val reply = itemView.findViewById<LinearLayout>(R.id.reply)
        val reply2 = itemView.findViewById<LinearLayout>(R.id.reply2)

        val username = itemView.findViewById<TextView>(R.id.userName)
        val repliedToMessage = itemView.findViewById<TextView>(R.id.replied_to_message)
        val sentMessage = itemView.findViewById<TextView>(R.id.sent_message)

        val username2 = itemView.findViewById<TextView>(R.id.userName2)
        val repliedToMessage2 = itemView.findViewById<TextView>(R.id.replied_to_message2)
        val sentMessage2 = itemView.findViewById<TextView>(R.id.sent_message2)

    }
}