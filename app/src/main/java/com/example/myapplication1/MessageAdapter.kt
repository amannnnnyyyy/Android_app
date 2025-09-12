package com.example.myapplication1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isGone
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Visibility

class MessageAdapter(val messages: List<Message>) : RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MessageViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.messages_layout, parent, false)
    return MessageViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: MessageViewHolder,
        position: Int
    ) {
        val to_be_sent = messages[position]

        holder.itemView.apply{
            val user_name = findViewById<TextView>(R.id.userName)
            val replied_to_message = findViewById<TextView>(R.id.replied_to_message)
            val sent_message = findViewById<TextView>(R.id.sent_message)
            val reply = findViewById<LinearLayout>(R.id.reply)

            val user_name2 = findViewById<TextView>(R.id.userName2)
            val replied_to_message2 = findViewById<TextView>(R.id.replied_to_message2)
            val sent_message2 = findViewById<TextView>(R.id.sent_message2)
            val reply2 = findViewById<LinearLayout>(R.id.reply2)

            val sent = findViewById<ConstraintLayout>(R.id.message_bubble)
            val received = findViewById<ConstraintLayout>(R.id.message_bubble2)

            if(to_be_sent.type=="sent")
            {
                received.visibility = View.GONE

                if(to_be_sent.repliedTo!=null){
                    user_name.text = to_be_sent.repliedTo
                    replied_to_message.text = to_be_sent.originalMessage
                }else{
                    reply.visibility = View.GONE
                }
                sent_message.text = to_be_sent.message
            }else if(to_be_sent.type=="received"){
                sent.visibility = View.GONE

                if(to_be_sent.repliedTo!=null){
                    user_name2.text = to_be_sent.repliedTo
                    replied_to_message2.text = to_be_sent.originalMessage
                }
                else{
                    reply2.visibility = View.GONE
                }
                sent_message2.text = to_be_sent.message
            }
        }
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    inner class MessageViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
}