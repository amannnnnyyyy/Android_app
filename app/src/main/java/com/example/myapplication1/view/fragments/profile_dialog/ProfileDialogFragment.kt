package com.example.myapplication1.view.fragments.profile_dialog

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionInflater
import com.example.myapplication1.R
import com.example.myapplication1.databinding.FragmentProfileDialogBinding
import com.example.myapplication1.databinding.ProfileDialogFragmentBinding
import com.example.myapplication1.view.fragments.chat_holder.ChatHolderFragmentDirections
import com.example.myapplication1.view.fragments.contact_list.ContactListFragmentDirections
import com.example.myapplication1.view.main.MyChatViewModel
import kotlin.properties.Delegates

class ProfileDialogFragment : DialogFragment(R.layout.fragment_profile_dialog) {

    var contactId: Int? = null
    var hereFrom: String? = null
    val activityVModel: MyChatViewModel by activityViewModels()

    private var _binding: FragmentProfileDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            contactId = it.getInt("contact_id")
            hereFrom = it.getString("from")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedElementEnterTransition = TransitionInflater.from(activity).inflateTransition(android.R.transition.move)
        sharedElementReturnTransition = TransitionInflater.from(activity).inflateTransition(android.R.transition.move)

        _binding = FragmentProfileDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activityVModel.contact.observe(viewLifecycleOwner){contacts ->
            val contact = contacts.find { it.contactId == contactId }

            contact?.let { con->
                if (con.profilePic!= Uri.EMPTY) binding.profilePicture.setImageURI(con.profilePic)
                else binding.profilePicture.setImageResource(R.drawable.profile_picture)

                binding.profileName.text = con.name.ifEmpty { "Unknown" }

                var contactDetailsDirection: NavDirections? = null
                val direction = when(hereFrom) {
                    "contacts" ->{
                        contactDetailsDirection = ContactListFragmentDirections.actionContactListFragmentToContactDetailsFragment(con.contactId)
                        ContactListFragmentDirections.actionContactListFragmentToChatDetailFragment(
                            1,
                            con.contactId
                        )
                    }

                    "chats" -> {
                        contactDetailsDirection = ChatHolderFragmentDirections.actionChatHolderFragmentToContactDetailsFragment(con.contactId)
                        ChatHolderFragmentDirections.actionChatHolderFragmentToChatDetailFragment(
                            1,
                            con.contactId
                        )
                    }
                    else -> null
                }
                binding.messages.setOnClickListener {
                    val nav = findNavController()
                        direction?.let {
                            nav.navigate(it)
                        }
                }

                binding.info.setOnClickListener {
                    val nav = findNavController()
                    contactDetailsDirection?.let { nav.navigate(it) }
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        _binding = null
        dismiss()
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileDialogFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}