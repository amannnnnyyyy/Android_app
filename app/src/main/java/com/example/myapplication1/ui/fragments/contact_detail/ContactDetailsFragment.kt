package com.example.myapplication1.ui.fragments.contact_detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication1.R
import com.example.myapplication1.databinding.ContactDetailsFragmentBinding

class ContactDetailsFragment : Fragment(R.layout.contact_details_fragment) {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = ContactDetailsFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }
}