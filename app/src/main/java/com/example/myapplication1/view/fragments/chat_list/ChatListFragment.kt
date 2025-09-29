package com.example.myapplication1.view.fragments.chat_list

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.Surface
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.MenuProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication1.R
import com.example.myapplication1.core.model.chat.Chat
import com.example.myapplication1.core.model.chat.ChatModel
import com.example.myapplication1.databinding.FragmentChatDetailBinding
import com.example.myapplication1.databinding.FragmentChatListBinding
import com.example.myapplication1.databinding.FragmentContactListBinding
import com.example.myapplication1.view.main.MyChatViewModel
import com.example.myapplication1.view.adapters.recycler_view_adapter.ChatListRecyclerViewAdapter
import com.example.myapplication1.view.adapters.recycler_view_adapter.ListenerType
import com.example.myapplication1.view.fragments.chat_holder.ChatHolderFragmentDirections
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlin.getValue


class ChatListFragment : Fragment(R.layout.fragment_chat_list){
    val viewModel: ChatListViewModel by viewModels()
    val myChatViewModel: MyChatViewModel by activityViewModels()

    private lateinit var recycler: RecyclerView;
    private lateinit var bottomNav: BottomNavigationView
    private lateinit var searchBtn: EditText;
    private var binding: FragmentChatListBinding? = null

    private var keyBoardIsVisible: Boolean = false

    private var menuProvider:MenuProvider? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatListBinding.inflate(inflater, container, false)

        menuProvider = myMenuProvider()


        binding?.let { bind->
            recycler = bind.recyclerView
            bottomNav = bind.filterBtn
            searchBtn = bind.searchArea

            bind.searching.setOnClickListener {
                bind.searchArea.requestFocus()
//            binding.appTitle.visibility = View.GONE
//            binding.chatListTopIcons.visibility = View.GONE
                bind.searching.visibility = View.VISIBLE
                activity?.let {
                    WindowCompat.getInsetsController(it.window, bind.searching).show(WindowInsetsCompat.Type.ime())
                }
            }


            ViewCompat.setOnApplyWindowInsetsListener(bind.root){view, insets ->
                keyBoardIsVisible = insets.isVisible(WindowInsetsCompat.Type.ime())
                if (keyBoardIsVisible)
                {
                    bottomNav.visibility = View.GONE
                } else {
                    bottomNav.visibility = View.VISIBLE
                }
                insets
            }


            bind.newChat.setOnClickListener {
                val nav = findNavController()
                val direction = ChatHolderFragmentDirections.actionChatHolderFragmentToContactListFragment2()
                nav.navigate(direction)
            }
        }


        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar: Toolbar? = view.findViewById<Toolbar>(R.id.toolbar)
        binding?.toolbar.let {
            (activity as? AppCompatActivity)?.setSupportActionBar(toolbar)
        }

        myChatViewModel.contact.observe(viewLifecycleOwner) { contacts ->
            viewModel.setUpChat(contacts, false)
        }

        viewModel.chats.observe(viewLifecycleOwner) { chats ->
            updateUI(chats)
        }
    }

    fun dynamicAdapter(recycler: RecyclerView, type:String, searchString:String?=null, owner: LifecycleOwner){
        val chats: List<Chat> = viewModel.getFilteredChats(type, owner =owner ,searchString)

        val adapter = ChatListRecyclerViewAdapter(chats,
            {
                when(it){
                    is ListenerType.ItemClick -> onClick(it.chatId,it.contactId)
                    is ListenerType.SearchClick -> {
                        dynamicAdapter(recycler, "searching", it.searchString.toString(), owner = viewLifecycleOwner)
                    }
                }
            }
            )
        recycler.adapter = adapter

        Log.i("howMany","Adapter")
        val rotation = context?.display?.rotation ?: 0
        when (rotation) {
            Surface.ROTATION_0 -> rotated(0)
            Surface.ROTATION_90 -> rotated(90)
            Surface.ROTATION_180 -> rotated(180)
            Surface.ROTATION_270 -> rotated(270)
            else -> "Unknown"
        }
    }

     fun onClick(chatId: Int, contactId:Int) {
        val navController = findNavController()
        val action = ChatHolderFragmentDirections.actionChatHolderFragmentToChatDetailFragment(chatId, contactId)
        navController.navigate(action)
    }



    private fun updateUI(chats: List<Chat>){
        val adapter = ChatListRecyclerViewAdapter(
            chats.filter { it.hasMessage },
            {
                when (it) {
                    is ListenerType.ItemClick -> onClick(
                        it.chatId,
                        it.contactId
                    )

                    is ListenerType.SearchClick -> {
                        dynamicAdapter(recycler, "searching", it.searchString.toString(), owner = viewLifecycleOwner)
                    }
                }
            }
            )

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.all -> viewModel.changeDisplayedChatType("all")
                R.id.fav -> viewModel.changeDisplayedChatType("fav")
                R.id.unread -> viewModel.changeDisplayedChatType("unread")
                R.id.groups -> viewModel.changeDisplayedChatType("groups")
            }
            true

        }

        viewModel.choiceToDisplayChats.observe(viewLifecycleOwner){ type->
            Log.i("howMany","choice too?")
            dynamicAdapter(recycler, type, owner = viewLifecycleOwner)
        }


        searchBtn.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int){}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int)=dynamicAdapter(recycler, "searching", s.toString(), owner = viewLifecycleOwner)
        })

        dynamicAdapter(recycler, viewModel.choiceToDisplayChats.value, owner = viewLifecycleOwner)
        //recycler.adapter = adapter
        //recycler.layoutManager = LinearLayoutManager(requireContext())
    }

    fun rotated(degree:Int){
       binding?.let{ bind->
           if (degree==90 || degree==180 || degree==270){
               bind.search.visibility = View.GONE
               bind.filterBtn.visibility = View.GONE
               bind.filterBtn.layoutParams.height = 0
               Log.i("howMany","Added")
              menuProvider?.let { requireActivity().addMenuProvider(it, viewLifecycleOwner, Lifecycle.State.RESUMED) }
              val params =  bind.recyclerView.layoutParams as ConstraintLayout.LayoutParams
             //  params.topToBottom = bind.chatListTopIcons.id
               recycler.layoutManager = GridLayoutManager(requireContext(),2)
          //     bind.moreOptions.setOnClickListener {

            //   }
           }else{
               menuProvider?.let { requireActivity().removeMenuProvider(it) }
               bind.search.visibility = View.VISIBLE
               bind.filterBtn.visibility = View.VISIBLE
               recycler.layoutManager = LinearLayoutManager(requireContext())
           }
       }
    }



    fun handleMenu(item: MenuItem): Boolean {
        Log.i("clicking", "I am clicking ${item.itemId}")
        return when(item.itemId){
            R.id.all->{
                viewModel.changeDisplayedChatType("all")
                Toast.makeText(context,"Clicked on ${item.itemId}",Toast.LENGTH_SHORT).show()
                true
            }
            R.id.fav -> {viewModel.changeDisplayedChatType("fav"); true}
            R.id.unread -> {viewModel.changeDisplayedChatType("unread"); true}
            R.id.groups -> {viewModel.changeDisplayedChatType("groups"); true}
            else ->  false
        }
    }


    fun myMenuProvider(): MenuProvider{
        return object: MenuProvider {
            override fun onCreateMenu(
                menu: Menu,
                menuInflater: MenuInflater
            ) {
                menuInflater.inflate(R.menu.chat_filter_menu, menu)
            }

            override fun onPrepareMenu(menu: Menu) {
                val more = menu.findItem(R.id.all)
                // more?.isVisible = false
                super.onPrepareMenu(menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return handleMenu(menuItem)
            }

        }
    }


}