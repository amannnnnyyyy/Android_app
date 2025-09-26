package com.example.myapplication1.view.fragments.contact_detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.example.myapplication1.ContactsViewModel
import com.example.myapplication1.R
import com.example.myapplication1.core.model.contact.ContactModel
import com.example.myapplication1.databinding.ContactDetailsFragmentBinding
import com.example.myapplication1.view.main.MyChatViewModel

class ContactDetailsFragment : Fragment(R.layout.contact_details_fragment) {
    private val contactDetailArgs: ContactDetailsFragmentArgs by navArgs()

    private val contactDetailsViewModel: ContactDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedElementEnterTransition = TransitionInflater.from(activity).inflateTransition(android.R.transition.move)
        sharedElementReturnTransition = TransitionInflater.from(activity).inflateTransition(android.R.transition.move)

        val binding = ContactDetailsFragmentBinding.inflate(inflater, container, false)

        binding.goBack.setOnClickListener { findNavController().navigateUp() }

        contactDetailsViewModel.fetchContactDetail(contactDetailArgs.contactId)//contactDetailArgs.contactId)
//        contactDetailsViewModel.contactDetail.observe()

        contactDetailsViewModel.contactDetails.observe(viewLifecycleOwner){ contact->
            contact?.let { con ->
                binding.userName.text = con.name
                binding.phoneNumber.text = con.phoneNumber
                binding.userProfilePicture.setImageURI(con.profilePic)
            }
        }
        return binding.root
    }
}