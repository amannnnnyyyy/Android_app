package com.example.myapplication1.ui.fragments.chat_detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication1.R
import com.example.myapplication1.core.model.chat.Chat
import com.example.myapplication1.core.model.chat.ChatModel
import com.example.myapplication1.core.model.contact.Contact
import com.example.myapplication1.core.model.contact.ContactModel
import com.example.myapplication1.core.model.message.Message
import com.example.myapplication1.core.model.message.MessageModel
import com.example.myapplication1.databinding.FragmentChatDetailBinding
import com.example.myapplication1.ui.adapters.recycler_view_adapter.MessagesRecyclerViewAdapter
import kotlin.getValue

class ChatDetailFragment : Fragment(R.layout.fragment_chat_detail) {
    private val chatDetailArgs: ChatDetailFragmentArgs by navArgs()
    private var contact: Contact? = null
    private var chat: Chat? = null

    private var messages: List<Message> = emptyList()
    private var adapter: RecyclerView.Adapter<MessagesRecyclerViewAdapter.MessagesViewHolder>? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentChatDetailBinding.inflate(inflater, container, false)

        val chatId = chatDetailArgs.chatId


        chat = ChatModel.chats.find { it.id == chatId }

        chat?.let { ch ->
            contact = ContactModel.contacts.find {
                it.id == ch.sender
            }

            messages = MessageModel.messagesList.filter { msg->
                msg.chatId == ch.id
            }
        }

        contact?.let{ cont->
            binding.userProfile.setImageURI(cont.profilePic)
            binding.userName.text = cont.name
        }

        chat?.let{ch ->
            adapter = MessagesRecyclerViewAdapter(messages)
            binding.messagesRecycler.adapter = adapter
            binding.messagesRecycler.layoutManager = LinearLayoutManager(requireContext())
        }


    return binding.root
    }
}