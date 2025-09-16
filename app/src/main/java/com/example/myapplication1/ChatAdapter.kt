package com.example.myapplication1

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView

class ChatAdapter(val chatList: List<Chats>): RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {
    private var onClickListener: OnItemClickListener? = null

    fun interface OnItemClickListener {
        fun onItemClick(position: Int, context: Context)
    }

    fun setOnclickListener(listener: OnItemClickListener) {
        this.onClickListener = listener
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ChatViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val view = inflater.inflate(R.layout.chats, parent,false)

        return ChatViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ChatViewHolder,
        position: Int
    ) {
        val chat = chatList[position]

        holder.itemView.apply{
            val profileImageField = findViewById<ShapeableImageView>(R.id.userProfile)
            val notificationField = findViewById<TextView>(R.id.notification)
            val nameField = findViewById<TextView>(R.id.name)
            val messageField = findViewById<TextView>(R.id.message)
            val timeField = findViewById<TextView>(R.id.time)

            nameField.text = if (chat.sender!=null)
                                chat.sender.name
                        else if (chat.phoneNumber!=null) chat.phoneNumber
                        else "Unknown"

            messageField.text = if (chat.sender!=null)
                                    chat.messages?.last()?.message
                              else "not yet implemented"

            profileImageField.setImageURI(if (chat.sender!=null)
                                            chat.sender.profilePicture.toUri()
                                          else Uri.EMPTY
                                )
            val unreadMessages: Int? = chat.sender?.messages?.filter {
                it.readStatus == ReadStatus.UNREAD
            }?.size

            if (unreadMessages != null  && unreadMessages!=0)
                notificationField.text = unreadMessages.toString()
            else
                notificationField.visibility = View.GONE

            timeField.text = chat.timeSent.toString()
        }

        holder.itemView.setOnClickListener {
            onClickListener?.onItemClick(position, holder.itemView.context)
        }

    }

    override fun getItemCount(): Int {
        return chatList.size
    }

    inner class ChatViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
}