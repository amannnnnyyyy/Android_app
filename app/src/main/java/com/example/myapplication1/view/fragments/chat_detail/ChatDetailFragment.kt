package com.example.myapplication1.view.fragments.chat_detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionInflater
import com.example.myapplication1.R
import com.example.myapplication1.core.model.chat.Chat
import com.example.myapplication1.core.model.chat.ChatModel
import com.example.myapplication1.core.model.contact.Contact
import com.example.myapplication1.core.model.contact.ContactModel
import com.example.myapplication1.core.model.message.Message
import com.example.myapplication1.core.model.message.MessageModel
import com.example.myapplication1.databinding.FragmentChatDetailBinding
import com.example.myapplication1.view.adapters.recycler_view_adapter.MessagesRecyclerViewAdapter
import com.example.myapplication1.view.fragments.chat_list.ChatListViewModel
import com.example.myapplication1.view.main.MyChatViewModel
import kotlin.getValue

class ChatDetailFragment : Fragment(R.layout.fragment_chat_detail) {
    private val chatDetailArgs: ChatDetailFragmentArgs by navArgs()
    private var contact: Contact? = null
    private var chat: Chat? = null
    private var messages: List<Message> = emptyList()
    private var adapter: RecyclerView.Adapter<MessagesRecyclerViewAdapter.MessagesViewHolder>? = null

    private val chatListViewModel: ChatListViewModel by viewModels()
    private val viewModel: ChatDetailViewModel by viewModels()
    private val activityViewModel: MyChatViewModel by activityViewModels()

    private var binding: FragmentChatDetailBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedElementEnterTransition = TransitionInflater.from(activity).inflateTransition(android.R.transition.move)
        sharedElementReturnTransition = TransitionInflater.from(activity).inflateTransition(android.R.transition.move)

        binding = FragmentChatDetailBinding.inflate(inflater, container, false)

        val chatId = chatDetailArgs.chatId


        chat = ChatModel.chats.find { it.id == chatId }

        chat?.let { ch ->
            contact = ContactModel.contacts.find {
                it.contactId == ch.sender
            }

            messages = MessageModel.messagesList.filter { msg->
                msg.chatId == ch.id
            }
        }




    return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        activityViewModel.contact.observe(viewLifecycleOwner){ con->
            chatListViewModel.setUpChat(con,false)
            Log.i("amIHere","nothing yet ${chatListViewModel.chats.value}  $con")
            chatListViewModel.chats.observe(viewLifecycleOwner){ch->
                viewModel.setUpChatDetails(ch)
                Log.i("amIHere","chat details are available")
                viewModel.chatDetails.observe(viewLifecycleOwner){
                    Log.i("amIHere","Detail view update")
                    updateUI(binding!!)
                }
            }
        }
    }


    fun updateUI(binding: FragmentChatDetailBinding){
        binding.goBack.setOnClickListener { findNavController().navigateUp() }

        contact?.let{ cont->
            binding.userProfile.setImageURI(cont.profilePic)
            binding.userName.text = cont.name
            binding.phoneNumber.text = cont.phoneNumber
            binding.wholeHeader.setOnClickListener {
                val extras  = FragmentNavigatorExtras(
                    binding.userProfile to "profile_pic"
                )
                val nav = findNavController()
                Toast.makeText(requireContext(),"to detail",Toast.LENGTH_SHORT).show()
                val direction = ChatDetailFragmentDirections.actionChatDetailFragmentToContactDetailsFragment(cont.contactId)
                nav.navigate(direction,extras)
            }
        }

        chat?.let{ch ->
            adapter = MessagesRecyclerViewAdapter(messages)
            binding.messagesRecycler.adapter = adapter
            binding.messagesRecycler.layoutManager = LinearLayoutManager(requireContext())
        }
    }
}