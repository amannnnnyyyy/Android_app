package com.example.myapplication1.ui.fragments.chat_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication1.R
import com.example.myapplication1.data.model.chat.Chat
import com.example.myapplication1.data.model.chat.ChatModel
import com.example.myapplication1.data.model.message.MessageModel
import com.example.myapplication1.data.model.message.ReadStatus
import com.example.myapplication1.databinding.FragmentChatListBinding
import com.example.myapplication1.ui.adapters.recycler_view_adapter.ChatListRecyclerViewAdapter


class ChatListFragment : Fragment(R.layout.fragment_chat_list) {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentChatListBinding.inflate(inflater,container,false)

        val recycler = binding.recyclerView
        val bottomNav = binding.filterBtn

        val adapter = ChatListRecyclerViewAdapter(ChatModel.chats)

        bottomNav.setOnItemSelectedListener {item ->
            when(item.itemId){
                R.id.all -> dynamicAdapter(recycler,"all")
                R.id.fav -> dynamicAdapter(recycler,"fav")
                R.id.unread -> dynamicAdapter(recycler,"unread")
                R.id.groups -> dynamicAdapter(recycler,"groups")
            }
            true

        }



        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(requireContext())


        return binding.root
    }

    fun dynamicAdapter(recycler: RecyclerView, type:String){
        val chatList: List<Chat> = when(type){
            "all" ->    ChatModel.chats
            "fav" ->    ChatModel.chats.filter { it.favourite }
            "unread" -> ChatModel.chats.filter { ch->
                            MessageModel.messagesList.any { msg -> msg.readStatus == ReadStatus.NOT_READ }
                        }
            "groups" -> ChatModel.chats.filter { ch -> ch.group }
            else -> emptyList()
        }
        val adapter = ChatListRecyclerViewAdapter(chatList)
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(requireContext())
    }
}