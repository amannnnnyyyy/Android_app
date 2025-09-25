package com.example.myapplication1.view.fragments.chat_list

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
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
import com.example.myapplication1.view.main.MyChatViewModel
import com.example.myapplication1.view.adapters.recycler_view_adapter.ChatListRecyclerViewAdapter
import com.example.myapplication1.view.fragments.chat_holder.ChatHolderFragmentDirections
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlin.getValue


class ChatListFragment : Fragment(R.layout.fragment_chat_list), ChatListRecyclerViewAdapter.OnItemClickListener {
    val viewModel: ChatListViewModel by viewModels()
    val activityViewModel: MyChatViewModel by viewModels()

    private lateinit var recycler: RecyclerView;
    private lateinit var bottomNav: BottomNavigationView
    private lateinit var searchBtn: EditText;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i("destinationFragment", "Inside chat list 1 ${ChatModel.chats}")

        val binding = FragmentChatListBinding.inflate(inflater, container, false)
        recycler = binding.recyclerView
        bottomNav = binding.filterBtn
        searchBtn = binding.searchArea

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


            activityViewModel.contact.observe(viewLifecycleOwner){ contacts->
                viewModel.setUpChat(contacts,false)
                viewModel.chats.observe(viewLifecycleOwner) { chats ->
                    val chatsFromModel = ChatModel.chats
                    Log.i("destinationFragment", "Inside chat list 21 $chats")
                    updateUI(chats)

            }
        }
    }

    fun dynamicAdapter(chatList:List<Chat>, recycler: RecyclerView, type:String, searchString:String?=null){
        val chats: List<Chat> = when(type){
            "all" ->    chatList.filter { it.hasMessage }
            "fav" ->    chatList.filter { it.favourite && it.hasMessage }
            "unread" -> chatList.filter { ch->
                            MessageModel.messagesList.any { msg -> msg.readStatus == ReadStatus.NOT_READ && ch.hasMessage }
                        }
            "groups" -> chatList.filter { ch -> ch.group && ch.hasMessage}

            "searching"->{
                chatList.filter { chat ->
                    ContactModel.contacts.filter{ contact ->
                        contact.name.contains(searchString?:"",ignoreCase = true)
                    }.any {
                        it.id == chat.sender
                    }
                }
            }
            else -> emptyList()
        }

        val adapter = ChatListRecyclerViewAdapter(chats)
        recycler.adapter = adapter
        adapter.setClickListener(this)
        recycler.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onClick(chatId: Int) {
        val navController = findNavController()
        val action = ChatHolderFragmentDirections.actionChatHolderFragmentToChatDetailFragment(chatId)
        navController.navigate(action)
    }



    private fun updateUI(chats: List<Chat>){
        val adapter = ChatListRecyclerViewAdapter(chats.filter { it.hasMessage })
        adapter.setClickListener(this)

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.all -> dynamicAdapter(chats,recycler, "all")
                R.id.fav -> dynamicAdapter(chats,recycler, "fav")
                R.id.unread -> dynamicAdapter(chats,recycler, "unread")
                R.id.groups -> dynamicAdapter(chats,recycler, "groups")
            }
            true

        }

        searchBtn.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int)=dynamicAdapter(chats,recycler, "searching", s.toString())
        })

        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(requireContext())
    }
}