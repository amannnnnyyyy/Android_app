package com.example.myapplication1.view.fragments.contact_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication1.R
import com.example.myapplication1.core.model.contact.Contact
import com.example.myapplication1.databinding.FragmentContactListBinding
import com.example.myapplication1.view.adapters.recycler_view_adapter.ContactsListRecyclerViewAdapter
import com.example.myapplication1.view.main.MyChatViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ContactListFragment : Fragment(R.layout.fragment_contact_list) {
    private val myChatViewModel : MyChatViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentContactListBinding.inflate(inflater,container, false)

        myChatViewModel.contact.observe(viewLifecycleOwner){
            lifecycleScope.launch {

                binding.progressBar.visibility = View.VISIBLE
                delay(1000)
                binding.progressBar.visibility = View.GONE
                updateUI(it, binding)
            }
        }

        return binding.root
    }

    fun updateUI(contacts:List<Contact>, binding: FragmentContactListBinding){
        binding.goBack.setOnClickListener { findNavController().popBackStack() }
        val adapter = ContactsListRecyclerViewAdapter(contacts)
        binding.recycler.adapter = adapter
        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        binding.contactNumbers.text = getString(R.string.contacts, contacts.size)
    }

}