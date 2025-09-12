package com.example.myapplication1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ContactMessages : Fragment() {

    val messagesList = listOf<Message>(
        Message(null, null, "How are you?", "received"),
        Message("John Adams", "How are you?", "I am fine. How are you?", "sent"),
        Message("You", "I am fine. How are you?", "I am good."),
        Message(null, null, "How was your stay at the hotel?", "received"),
        Message("John Adams", "How was your stay at the hotel?", "It was fine, it ain't much to talk about tho, I've been staying in a 4-start hotel and they're hospitable", "sent"),
        Message("John Adams", "How was your stay at the hotel?", "It was fine, it ain't much to talk about tho, I've been staying in a 4-start hotel and they're hospitable", "received"),
        Message("John Adams", "How was your stay at the hotel?", "It was fine, it ain't much to talk about tho, I've been staying in a 4-start hotel and they're hospitable", "sent")
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_contact_messages, container, false)

        val adapter = MessageAdapter(messagesList)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewMessages)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(view.context)

        return view
    }

}