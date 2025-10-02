package com.example.myapplication1

import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.ScaleAnimation
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.navigation.Navigator
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDate

class Chats_display_fragment : Fragment(), ChatAdapter.OnItemClickListener {
    @RequiresApi(Build.VERSION_CODES.O)
    private val chatList: List<Chats> = listOf(
        Chats(
            Contact(
                1,
                mutableListOf(
                    Message(1, null, null, "Hello", "received", ReadStatus.UNREAD),
                    Message(1, null, null, "Hey there", "received", ReadStatus.UNREAD)
                ),
                Uri.EMPTY.toString(),
                name = "john",
                messageDescription = "New User",
                phoneNumber = "+9719782367986"
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
                name = "jack",
                messageDescription = "New User",
                phoneNumber = "+9719782000086"
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
                name = "smith",
                messageDescription = "New User",
                phoneNumber = "+9719787777986"
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
                name = "jason",
                messageDescription = "New User",
                phoneNumber = "+97197823968858"
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
                messageDescription = "New User",
                phoneNumber = "+9719782333333"
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
                name = "drago",
                messageDescription = "New User",
                phoneNumber = "+9719782888888"
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
                name = "felix",
                messageDescription = "New User",
                phoneNumber = "+9719782367111"
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
                name = "clarence",
                messageDescription = "New User",
                phoneNumber = "+9719782365555"
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
                name = "florence",
                messageDescription = "New User",
                phoneNumber = "+9719782367777"
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
                name = "trevor",
                messageDescription = "New User",
                phoneNumber = "+971978565565466"
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
                name = "noah",
                messageDescription = "New User",
                phoneNumber = "+9719782344444"
            ),
            phoneNumber = null,
            status = ChatSeenStatus.Unread,
            timeSent = LocalDate.now()
        )
    )
    var chat: Chats? = null

    private var param1: String? = null
    private var param2: String? = null

    lateinit var camera: ImageView
    lateinit var search: ImageView
    lateinit var more: ImageView
    lateinit var new_chat: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_chats_display_fragment, container, false)


        val adapter = ChatAdapter(chatList)

        val chatRecycler = view.findViewById<RecyclerView>(R.id.recyclerView)

         chatRecycler.adapter = adapter

         adapter.setOnclickListener(this)

         chatRecycler.layoutManager = LinearLayoutManager(view.context)


    return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        new_chat = view.findViewById<ImageView>(R.id.new_chat)

            new_chat.setOnClickListener {
                val navController = findNavController()
                val action = MainChatsDirections.actionMainChatsToContactsList2()
                navController.navigate(action)
            }

    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Chats_display_fragment().apply {
                arguments = Bundle().apply {

                }
            }
    }



    @RequiresApi(Build.VERSION_CODES.O)
    override fun onItemClick(
        position: Int,
        context: Context,
        type: String,
        viewSent: View?,
        motionEvent: MotionEvent?
    ) {
        if (type=="normal"){
            chatList[position].messages?.forEach {
                it.readStatus = ReadStatus.READ
            }


            val args = Bundle()
            args.apply {
                putSerializable("contact",chatList.elementAt(position).sender)
            }

            val profilePic: ImageView

            val extras: FragmentNavigator.Extras


            val nav = findNavController()
            val action = MainChatsDirections.actionMainChatsToContactMessages(chatList[position])
            if(viewSent!=null){
                profilePic= viewSent.findViewById<ImageView>(R.id.userProfile)

                extras = FragmentNavigatorExtras(profilePic to "profile_pic")
                nav.navigate(action,extras)
            }



        }else if(type=="dialog"){

            val name = chatList[position].sender?.name?:chatList[position].phoneNumber?:"Unknown"
            val contact = chatList[position].sender
            if (view != null ) {
                view?.let { v->
                    contact?.let{ c->
                        openProfileDetail(position,name, c, v)
                    }
                }
            }
         //   val profileDialog = ProfileDialog()
//            val bundle = Bundle().apply {
//                putString("name",chatList[position].sender?.name?:chatList[position].phoneNumber?:"Unknown")
//                putSerializable("Contact",chatList[position].sender)
//            }

//            profileDialog.arguments =bundle
//            profileDialog.show(childFragmentManager,"profile dialog")
        }
    }


    private fun openProfileDetail(position: Int, name:String, contact:Contact, viewSent:View){
        val profilePic: ImageView

        val extras: FragmentNavigator.Extras

        val nav = findNavController()
        val action = MainChatsDirections.actionMainChatsToProfileDialog("contactsList",contact)
        if(viewSent!=null){
            profilePic= viewSent.findViewById<ImageView>(R.id.userProfile)

            extras = FragmentNavigatorExtras(profilePic to "profile_pic")
            nav.navigate(action,extras)
        }
    }



}