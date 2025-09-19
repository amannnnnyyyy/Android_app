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
import androidx.navigation.fragment.FragmentNavigatorExtras
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
                name = "jack",
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
                name = "smith",
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
                name = "jason",
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
                name = "drago",
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
                name = "felix",
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
                name = "clarence",
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
                name = "florence",
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
                name = "trevor",
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
                name = "noah",
                messageDescription = "New User"
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

                new_chat = view.findViewById<ImageView>(R.id.new_chat)
        openNewChat(new_chat)

        val adapter = ChatAdapter(chatList)

        val chatRecycler = view.findViewById<RecyclerView>(R.id.recyclerView)

         chatRecycler.adapter = adapter

         adapter.setOnclickListener(this)

         chatRecycler.layoutManager = LinearLayoutManager(view.context)


    return view
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Chats_display_fragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    fun openNewChat(view:View){
        view.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.mainHolder, ContactsList())
                .addToBackStack("main_chat")
                .commit()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onItemClick(
        position: Int,
        context: Context,
        type: String,
        view: View?,
        motionEvent: MotionEvent?
    ) {
        if (type=="normal"){
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

            parentFragmentManager.beginTransaction().replace(R.id.mainHolder,contactMessages)
                .addToBackStack("contactPage").setReorderingAllowed(true).commit()
        }else if(type=="dialog"){

            val name = chatList[position].sender?.name?:chatList[position].phoneNumber?:"Unknown"
            val contact = chatList[position].sender
            if (view != null && motionEvent!=null) {
                openProfileDetail(position,name, contact, view,motionEvent)
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


    private fun openProfileDetail(position: Int, name:String, contact:Contact?, view:View, motionEvent: MotionEvent){
        val profileDialog = ProfileDialog()
        val chatsDisplay = Chats_display_fragment()

        val extras = FragmentNavigatorExtras(view to view.transitionName)

        ViewCompat.setTransitionName(view, "item_image")

        val slideInAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_up_in)



        ///////////////////
        val clickX = motionEvent.x
        val clickY = motionEvent.y

        // Get view dimensions
        val viewWidth = view.width.toFloat()
        val viewHeight = view.height.toFloat()

        val pivotX = clickX / viewWidth
        val pivotY = clickY / viewHeight


        ///////////////////





        val bundle = Bundle().apply{
            putString("transitionName", view.transitionName)
            putSerializable("name", name)
            putSerializable("Contact",contact)
            putFloat("pivotX",pivotX)
            putFloat("pivotY",pivotY)
        }
        Toast.makeText(requireContext(),view.transitionName, Toast.LENGTH_SHORT).show()

        profileDialog.arguments = bundle
     //   parentFragmentManager.beginTransaction().apply {  }
        profileDialog.show(childFragmentManager,"profile dialog")
    }



}