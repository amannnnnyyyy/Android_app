package com.example.myapplication1.ui.fragments.chat_list

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication1.R
import com.example.myapplication1.core.model.chat.Chat
import com.example.myapplication1.core.model.chat.ChatModel
import com.example.myapplication1.core.model.contact.ContactModel
import com.example.myapplication1.core.model.message.MessageModel
import com.example.myapplication1.core.model.message.ReadStatus
import com.example.myapplication1.databinding.FragmentChatListBinding
import com.example.myapplication1.main.MyChatViewModel
import com.example.myapplication1.ui.adapters.recycler_view_adapter.ChatListRecyclerViewAdapter
import com.example.myapplication1.ui.fragments.chat_holder.ChatHolderFragmentDirections
import kotlin.getValue


class ChatListFragment : Fragment(R.layout.fragment_chat_list), ChatListRecyclerViewAdapter.OnItemClickListener {
    val viewModel by viewModels<MyChatViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentChatListBinding.inflate(inflater,container,false)

        val recycler = binding.recyclerView
        val bottomNav = binding.filterBtn
        val searchBtn = binding.searchArea

        val adapter = ChatListRecyclerViewAdapter(ChatModel.chats.filter { it.hasMessage })
        adapter.setClickListener(this)


        bottomNav.setOnItemSelectedListener {item ->
            when(item.itemId){
                R.id.all -> dynamicAdapter(recycler,"all")
                R.id.fav -> dynamicAdapter(recycler,"fav")
                R.id.unread -> dynamicAdapter(recycler,"unread")
                R.id.groups -> dynamicAdapter(recycler,"groups")
            }
            true

        }



        searchBtn.addTextChangedListener(object: TextWatcher{
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
            ) {
                dynamicAdapter(recycler,"searching",s.toString())
            }

        })



        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(requireContext())


        return binding.root
    }

    fun dynamicAdapter(recycler: RecyclerView, type:String, searchString:String?=null){
        val chatList: List<Chat> = when(type){
            "all" ->    ChatModel.chats.filter { it.hasMessage }
            "fav" ->    ChatModel.chats.filter { it.favourite && it.hasMessage }
            "unread" -> ChatModel.chats.filter { ch->
                            MessageModel.messagesList.any { msg -> msg.readStatus == ReadStatus.NOT_READ && ch.hasMessage }
                        }
            "groups" -> ChatModel.chats.filter { ch -> ch.group && ch.hasMessage}

            "searching"->{
                ChatModel.chats.filter { chat ->
                    ContactModel.contacts.filter{ contact ->
                        contact.name.contains(searchString?:"",ignoreCase = true)
                    }.any {
                        it.id == chat.sender
                    }
                }
            }
            else -> emptyList()
        }

        val adapter = ChatListRecyclerViewAdapter(chatList)
        recycler.adapter = adapter
        adapter.setClickListener(this)
        recycler.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onClick(chatId: Int) {
        val navController = findNavController()
        val action = ChatHolderFragmentDirections.actionChatHolderFragmentToChatDetailFragment(chatId)
        navController.navigate(action)
        //Toast.makeText(requireContext(),"clicking, $chatId", Toast.LENGTH_SHORT).show()
    }
}