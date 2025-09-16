package com.example.myapplication1

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
                            Message(1,null,null,"Hello","received", ReadStatus.UNREAD)
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

class MainChats : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    lateinit var camera: ImageView
    lateinit var search: ImageView
    lateinit var more: ImageView

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

        val adapter = ChatAdapter(chatList)

        val chatRecycler = view.findViewById<RecyclerView>(R.id.recyclerView)

        chatRecycler.adapter = adapter

        chatRecycler.layoutManager = LinearLayoutManager(view.context)
    return view
    }

}