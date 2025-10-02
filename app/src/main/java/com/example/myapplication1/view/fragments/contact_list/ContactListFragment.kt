package com.example.myapplication1.view.fragments.contact_list

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication1.R
import com.example.myapplication1.core.model.contact.Contact
import com.example.myapplication1.databinding.FragmentContactListBinding
import com.example.myapplication1.view.adapters.recycler_view_adapter.ContactClicked
import com.example.myapplication1.view.adapters.recycler_view_adapter.ContactsListRecyclerViewAdapter
import com.example.myapplication1.view.fragments.profile_dialog.ProfileDialogFragment
import com.example.myapplication1.view.main.MyChatViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ContactListFragment : Fragment(R.layout.fragment_contact_list) {
    private val myChatViewModel : MyChatViewModel by activityViewModels()
    private val viewModel: ContactsListViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {

            }
        }
        val binding = FragmentContactListBinding.inflate(inflater,container, false)

        myChatViewModel.contact.observe(viewLifecycleOwner){
            lifecycleScope.launch {
                viewModel.setUpContacts(it)
                binding.progressBar.visibility = View.VISIBLE
                delay(400)
                binding.progressBar.visibility = View.GONE

                updateUI(it, binding)
            }
        }

        return binding.root
    }

    fun updateUI(contacts:List<Contact>, binding: FragmentContactListBinding){
        binding.goBack.setOnClickListener { findNavController().popBackStack() }
        binding.search.setOnClickListener {
            binding.header.visibility = View.GONE
            binding.searching.visibility = View.VISIBLE
            binding.searching.requestFocus()
            activity?.let {
                WindowCompat.getInsetsController(it.window, binding.searching).show(WindowInsetsCompat.Type.ime())
            }

            var searchingFor:String = ""

                binding.searchArea.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        Log.i("hereSearching","1 -it is the function")
                    }
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int){
                        Log.i("hereSearching","3 - it is the function")
                    }
                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int)
                        {
                        Log.i("hereSearching","2 - it is the function")
                        if (searchingFor!=s.toString()){
                            searchingFor = s.toString()
                            Log.i("compareString","${s.toString()}  $searchingFor")
                            dynamicAdapter("searching", binding, contacts, s.toString())
                        }else searchingFor = s.toString()
                          //  binding.searchArea.removeTextChangedListener(this)
                    }
                })
        }

        binding.backToContacts.setOnClickListener {
            binding.header.visibility = View.VISIBLE
            binding.searching.visibility = View.GONE
            val imm = view?.context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view?.windowToken, 0)
        }


        dynamicAdapter("normal", binding,contacts, null)

        binding.contactNumbers.text = getString(R.string.contacts, contacts.size)
    }

    fun dynamicAdapter(type:String, binding: FragmentContactListBinding, contacts: List<Contact>, searchString:String?=null){
        if (type=="searching"){
            Log.i("hereSearching","Sent for searching")
            viewModel.getFilteredChats(viewLifecycleOwner,searchString?:"")
            viewModel.filtered.observe(viewLifecycleOwner){ filteredContacts->
                val adapter = ContactsListRecyclerViewAdapter(filteredContacts,
                    {
                        when(it){
                            is ContactClicked.ItemClick -> navigateToChatDetails(it.chatId, it.contactId)
                            is ContactClicked.ProfileClick-> navigateToDialog(it.contactId)
                        }
                    })
                binding.recycler.adapter = adapter
                binding.recycler.layoutManager = LinearLayoutManager(requireContext())
            }
        }else{
            val adapter = ContactsListRecyclerViewAdapter(contacts,
                {
                    when(it){
                        is ContactClicked.ItemClick -> navigateToChatDetails(it.chatId, it.contactId)
                        is ContactClicked.ProfileClick-> navigateToDialog(it.contactId)
                    }
                })
            binding.recycler.adapter = adapter
            binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        }
    }


    fun navigateToChatDetails(chatId: Int, contactId: Int){
        val nav = findNavController()
        val direction = ContactListFragmentDirections.actionContactListFragmentToChatDetailFragment(chatId,contactId)
        nav.navigate(direction)
    }

    fun navigateToDialog(contactId: Int){
        val profileDialog =  ProfileDialogFragment()
        val bundle = Bundle().apply {
                putInt("contact_id",contactId)
                putString("from","contacts")
            }
        profileDialog.arguments = bundle
        profileDialog.show(childFragmentManager,null)
//        val nav = findNavController()
//        val direction = ContactListFragmentDirections.actionContactListFragmentToProfileDialogFragment()
//        nav.navigate(direction)
    }

}