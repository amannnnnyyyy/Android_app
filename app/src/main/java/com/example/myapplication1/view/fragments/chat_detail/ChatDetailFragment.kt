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
import androidx.lifecycle.lifecycleScope
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.getValue

class ChatDetailFragment : Fragment(R.layout.fragment_chat_detail) {
    private val chatDetailArgs: ChatDetailFragmentArgs by navArgs()
    private var contact: Contact? = null
    private var chat: Chat? = null
    private var messages: List<Message> = emptyList()
    private var adapter: RecyclerView.Adapter<MessagesRecyclerViewAdapter.MessagesViewHolder>? = null
    private val viewModel: ChatDetailViewModel by viewModels()
    private var binding: FragmentChatDetailBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedElementEnterTransition = TransitionInflater.from(activity).inflateTransition(android.R.transition.move)
        sharedElementReturnTransition = TransitionInflater.from(activity).inflateTransition(android.R.transition.move)

        binding = FragmentChatDetailBinding.inflate(inflater, container, false)

        binding?.progressBar?.visibility = View.VISIBLE
        binding?.profiles?.visibility = View.GONE
        binding?.headerButtons?.visibility = View.GONE
        binding?.messagesRecycler?.visibility = View.GONE

    return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.fetchSender(chatDetailArgs.contactId)

        if (viewModel.chatDetails.value?.isEmpty()?:true)
        {
            Log.i("isitempty","${viewModel.chatDetails.value} triggered")
            viewModel.setUpChatDetails(ChatModel.chats)
        }

        viewModel.sender.observe(viewLifecycleOwner){ con ->
            lifecycleScope.launch {
                delay(100)
                updateHeader(con, binding!!)
            }
        }
        viewModel.chatDetails.observe(viewLifecycleOwner){ msg->
            val chatIds:List<Int> = msg.map {
                it.chatId
            }
            Log.i("chatids",": $chatIds")
            messages = findSpecificMessage(chatDetailArgs.chatId, messages)
            updateUI(binding!!, msg)
        }
    }

    fun updateHeader(contact: Contact, binding: FragmentChatDetailBinding){
        contact.let{ cont->
            binding.progressBar.visibility = View.GONE
            binding.profiles.visibility = View.VISIBLE
            binding.headerButtons.visibility = View.VISIBLE
            binding.messagesRecycler.visibility = View.VISIBLE

            Log.i("chatids",": ${cont.contactId}")

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
    }


    fun updateUI(binding: FragmentChatDetailBinding, messageList:List<Message>){
        binding.goBack.setOnClickListener { findNavController().popBackStack() }

        adapter = MessagesRecyclerViewAdapter(messageList)
        binding.messagesRecycler.adapter = adapter
        binding.messagesRecycler.layoutManager = LinearLayoutManager(requireContext())
    }

    fun findSpecificMessage(chatId: Int, messages: List<Message>): List<Message> =  messages.filter { msg-> msg.chatId == chatId }

}