package com.example.myapplication1.view.adapters.recycler_view_adapter

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication1.R
import com.example.myapplication1.core.model.chat.Chat
import com.example.myapplication1.core.model.contact.Contact
import com.example.myapplication1.core.model.contact.ContactModel
import com.example.myapplication1.core.model.message.MessageModel
import com.example.myapplication1.core.model.message.ReadStatus
import com.google.android.material.imageview.ShapeableImageView

class ChatListRecyclerViewAdapter(
    chatList:List<Chat>, private val onUpdateChange: (ListenerType)-> Unit
): RecyclerView.Adapter<ChatListRecyclerViewAdapter.ChatListRecyclerViewHolder>() {
    val chats = chatList

    var onClickListener: OnItemClickListener? = null

    fun interface OnItemClickListener{
        fun onClick(chatId:Int, contactId: Int)
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
        @SuppressLint("RecyclerView") position: Int
    ) {
        val wholeSearch: LinearLayout = holder.itemView.findViewById<LinearLayout>(R.id.search)
        val searchField: EditText = holder.itemView.findViewById<EditText>(R.id.search_area)
        if (position == 0){
            wholeSearch.visibility = View.GONE
            searchField.addTextChangedListener(object: TextWatcher{
                override fun afterTextChanged(s: Editable?) {}

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {}

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {onUpdateChange.invoke(ListenerType.SearchClick(s))}

            })
            //{ edit->
//                Log.i("searching","Hey yo I'm tryna search")
//                onUpdateChange.invoke(ListenerType.SearchClick(edit as EditText))
//            }
        }else{
            wholeSearch.visibility = View.GONE
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

        contact?.let { con->
            chatsView.setOnClickListener {
                onUpdateChange.invoke(ListenerType.ItemClick(chat.id,con.contactId))
            }
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

sealed class ListenerType{
    class ItemClick(val chatId:Int, val contactId: Int): ListenerType()
    class SearchClick(val searchString: CharSequence?): ListenerType()
}

//interface UpdateListener{
//    fun onUpdateClicked(type: ListenerType)
//}