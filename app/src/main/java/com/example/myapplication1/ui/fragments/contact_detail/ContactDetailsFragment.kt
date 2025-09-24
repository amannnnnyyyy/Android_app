package com.example.myapplication1.ui.fragments.contact_detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.example.myapplication1.R
import com.example.myapplication1.core.model.chat.ChatModel
import com.example.myapplication1.core.model.contact.ContactModel
import com.example.myapplication1.core.model.message.MessageModel
import com.example.myapplication1.databinding.ContactDetailsFragmentBinding

class ContactDetailsFragment : Fragment(R.layout.contact_details_fragment) {

    private val contactDetailArgs: ContactDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = ContactDetailsFragmentBinding.inflate(inflater, container, false)

        val contactId = contactDetailArgs.contactId

        val contact = ContactModel.contacts.find { it.id == contactId }

        contact?.let { con->
            binding.userName.text = con.name
            binding.phoneNumber.text = con.phoneNumber
            binding.userProfilePicture.setImageURI(con.profilePic)
        }

        return binding.root
    }
}