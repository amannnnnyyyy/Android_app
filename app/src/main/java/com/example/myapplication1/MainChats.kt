package com.example.myapplication1

import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.time.LocalDate

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

@RequiresApi(Build.VERSION_CODES.O)
private val chatList: List<Chats> = listOf(
    Chats(
        Contact(
            1,
            mutableListOf(
                            Message(1,null,null,"Hello","received", ReadStatus.UNREAD),
                            Message(1,null,null,"Hey there","received", ReadStatus.UNREAD)
                ),
             Uri.EMPTY.toString(),
            name = "john",
            messageDescription = "New User"
            ),
        phoneNumber = null,
        status = ChatSeenStatus.Unread,
        timeSent = LocalDate.now()
    ),
    Chats(
        Contact(
            1,
            mutableListOf(
                Message(1,null,null,"Hello","received", ReadStatus.UNREAD),
                Message(1,null,null,"Hey there","received", ReadStatus.UNREAD)
            ),
            Uri.EMPTY.toString(),
            name = "john",
            messageDescription = "New User"
        ),
        phoneNumber = null,
        status = ChatSeenStatus.Unread,
        timeSent = LocalDate.now()
    ),
    Chats(
        Contact(
            1,
            mutableListOf(
                Message(1,null,null,"Hello","received", ReadStatus.UNREAD),
                Message(1,null,null,"Hey there","received", ReadStatus.UNREAD)
            ),
            Uri.EMPTY.toString(),
            name = "john",
            messageDescription = "New User"
        ),
        phoneNumber = null,
        status = ChatSeenStatus.Unread,
        timeSent = LocalDate.now()
    ),
    Chats(
        Contact(
            1,
            mutableListOf(
                Message(1,null,null,"Hello","received", ReadStatus.UNREAD),
                Message(1,null,null,"Hey there","received", ReadStatus.UNREAD)
            ),
            Uri.EMPTY.toString(),
            name = "john",
            messageDescription = "New User"
        ),
        phoneNumber = null,
        status = ChatSeenStatus.Unread,
        timeSent = LocalDate.now()
    ),
    Chats(
        Contact(
            1,
            mutableListOf(
                Message(1,null,null,"Hello","received", ReadStatus.UNREAD),
                Message(1,null,null,"Hey there","received", ReadStatus.UNREAD)
            ),
            Uri.EMPTY.toString(),
            name = "john",
            messageDescription = "New User"
        ),
        phoneNumber = null,
        status = ChatSeenStatus.Unread,
        timeSent = LocalDate.now()
    ),
    Chats(
        Contact(
            1,
            mutableListOf(
                Message(1,null,null,"Hello","received", ReadStatus.UNREAD),
                Message(1,null,null,"Hey there","received", ReadStatus.UNREAD)
            ),
            Uri.EMPTY.toString(),
            name = "john",
            messageDescription = "New User"
        ),
        phoneNumber = null,
        status = ChatSeenStatus.Unread,
        timeSent = LocalDate.now()
    ),
    Chats(
        Contact(
            1,
            mutableListOf(
                Message(1,null,null,"Hello","received", ReadStatus.UNREAD),
                Message(1,null,null,"Hey there","received", ReadStatus.UNREAD)
            ),
            Uri.EMPTY.toString(),
            name = "john",
            messageDescription = "New User"
        ),
        phoneNumber = null,
        status = ChatSeenStatus.Unread,
        timeSent = LocalDate.now()
    ),
    Chats(
        Contact(
            1,
            mutableListOf(
                Message(1,null,null,"Hello","received", ReadStatus.UNREAD),
                Message(1,null,null,"Hey there","received", ReadStatus.UNREAD)
            ),
            Uri.EMPTY.toString(),
            name = "john",
            messageDescription = "New User"
        ),
        phoneNumber = null,
        status = ChatSeenStatus.Unread,
        timeSent = LocalDate.now()
    ),
    Chats(
        Contact(
            1,
            mutableListOf(
                Message(1,null,null,"Hello","received", ReadStatus.UNREAD),
                Message(1,null,null,"Hey there","received", ReadStatus.UNREAD)
            ),
            Uri.EMPTY.toString(),
            name = "john",
            messageDescription = "New User"
        ),
        phoneNumber = null,
        status = ChatSeenStatus.Unread,
        timeSent = LocalDate.now()
    ),
    Chats(
        Contact(
            1,
            mutableListOf(
                Message(1,null,null,"Hello","received", ReadStatus.UNREAD),
                Message(1,null,null,"Hey there","received", ReadStatus.UNREAD)
            ),
            Uri.EMPTY.toString(),
            name = "john",
            messageDescription = "New User"
        ),
        phoneNumber = null,
        status = ChatSeenStatus.Unread,
        timeSent = LocalDate.now()
    ),
    Chats(
        Contact(
            1,
            mutableListOf(
                Message(1,null,null,"Hello","received", ReadStatus.UNREAD),
                Message(1,null,null,"Hey there","received", ReadStatus.UNREAD)
            ),
            Uri.EMPTY.toString(),
            name = "john",
            messageDescription = "New User"
        ),
        phoneNumber = null,
        status = ChatSeenStatus.Unread,
        timeSent = LocalDate.now()
    )
)

class MainChats : Fragment(), ChatAdapter.OnItemClickListener {
    private var param1: String? = null
    private var param2: String? = null

    lateinit var camera: ImageView
    lateinit var search: ImageView
    lateinit var more: ImageView
    lateinit var new_chat: ImageView

    var chat: Chats? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment_main_chats, container, false)

//        new_chat = view.findViewById<ImageView>(R.id.new_chat)
//        openNewChat(new_chat)

        //val adapter = ChatAdapter(chatList)

        //val chatRecycler = view.findViewById<RecyclerView>(R.id.recyclerView)

       // chatRecycler.adapter = adapter

       // adapter.setOnclickListener(this)

       // chatRecycler.layoutManager = LinearLayoutManager(view.context)

        val bottomNavigation = view.findViewById<BottomNavigationView>(R.id.footer)

        bottomNavigation.setOnNavigationItemSelectedListener{
            when(it.itemId){
                R.id.chats -> setFragment(Chats_display_fragment())
                R.id.calls -> setFragment(ContactsList())
            }
            true
        }
    return view
    }


    fun openNewChat(view:View){
        view.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.main, ContactsList())
                .addToBackStack("main_chat")
                .commit()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onItemClick(position: Int, context: Context) {
        //read messages
        chatList[position].messages?.forEach {
            it.readStatus = ReadStatus.READ
        }


        val args = Bundle()
        args.apply {
            putSerializable("contact",chatList.elementAt(position).sender)
        }

        val contactMessages = ContactMessages().apply{
            arguments = args
        }

        parentFragmentManager.beginTransaction().replace(R.id.main,contactMessages)
            .addToBackStack("contactPage").setReorderingAllowed(true).commit()

    }

    private fun setFragment(frag: Fragment){
        parentFragmentManager.beginTransaction().replace(R.id.chats_frag_holder,frag).commit()
    }

}