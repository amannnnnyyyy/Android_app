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
import com.example.myapplication1.data.model.message.Message
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

        var adapter : ChatListRecyclerViewAdapter = ChatListRecyclerViewAdapter(ChatModel.chats)

        bottomNav.setOnItemSelectedListener {item ->
            when(item.itemId){
                R.id.all->{
                    dynamicAdapter(ChatModel.chats,recycler)
                }
                R.id.fav -> {
                    dynamicAdapter(ChatModel.chats.filter { it.favourite },recycler)
                }
                R.id.unread -> {
                    dynamicAdapter(ChatModel.chats.filter { ch->
                        MessageModel.messagesList.any { msg ->
                            msg.readStatus == ReadStatus.NOT_READ
                        }
                    },recycler)
                }
                R.id.groups -> {
                    dynamicAdapter(ChatModel.chats.filter { ch ->
                        ch.group
                    },recycler)
                }
            }
            true

        }



        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(requireContext())


        return binding.root
    }

    fun dynamicAdapter(chats:List<Chat>, recycler: RecyclerView){
        val adapter = ChatListRecyclerViewAdapter(chats)
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(requireContext())
    }
}