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
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
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

    private var keyBoardIsVisible: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i("destinationFragment", "Inside chat list 1 ${ChatModel.chats}")

        val binding = FragmentChatListBinding.inflate(inflater, container, false)
        recycler = binding.recyclerView
        bottomNav = binding.filterBtn
        searchBtn = binding.searchArea


        ViewCompat.setOnApplyWindowInsetsListener(binding.root){view, insets ->
            keyBoardIsVisible = insets.isVisible(WindowInsetsCompat.Type.ime())
            val imeHeight = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom

            if (keyBoardIsVisible)
            {
                bottomNav.visibility = View.GONE
                binding.chatListTopIcons.visibility = View.GONE
                binding.appTitle.visibility = View.GONE
            } else {
                bottomNav.visibility = View.VISIBLE
                binding.chatListTopIcons.visibility = View.VISIBLE
                binding.appTitle.visibility = View.VISIBLE
            }

            insets
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

            activityViewModel.contact.observe(viewLifecycleOwner){ contacts->
                viewModel.setUpChat(contacts,false)
                viewModel.chats.observe(viewLifecycleOwner) { chats ->
                    Log.i("destinationFragment", "Inside chat list 21 $chats")
                    updateUI(chats)

            }
        }
    }

    fun dynamicAdapter(recycler: RecyclerView, type:String, searchString:String?=null, owner: LifecycleOwner){
        val chats: List<Chat> = viewModel.getFilteredChats(type, owner =owner ,searchString)

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
                R.id.all -> dynamicAdapter(recycler, "all", owner = viewLifecycleOwner)
                R.id.fav -> dynamicAdapter(recycler, "fav", owner = viewLifecycleOwner)
                R.id.unread -> dynamicAdapter(recycler, "unread", owner = viewLifecycleOwner)
                R.id.groups -> dynamicAdapter(recycler, "groups", owner = viewLifecycleOwner)
            }
            true

        }

        searchBtn.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int){}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int)=dynamicAdapter(recycler, "searching", s.toString(), owner = viewLifecycleOwner)
        })

        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(requireContext())
    }
}