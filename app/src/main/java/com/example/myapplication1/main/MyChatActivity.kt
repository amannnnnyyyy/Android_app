package com.example.myapplication1.main

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication1.R
import com.example.myapplication1.core.model.chat.ChatModel
import com.example.myapplication1.core.model.contact.Contact
import com.example.myapplication1.core.model.contact.ContactModel
import com.example.myapplication1.core.model.message.MessageModel

class MyChatActivity : AppCompatActivity() {
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                fetchContacts(this)
                Log.i("-----Contacts", ContactModel.contacts.toString())
            } else {
                Log.i("-----Contacts", "Permission denied")
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_my_chat)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
            == PackageManager.PERMISSION_GRANTED
        ) {
            fetchContacts(this)
        } else {
            requestPermissionLauncher.launch(Manifest.permission.READ_CONTACTS)
        }

    }


    fun fetchContacts(context: Context){
        val contactNames = ContactModel.fetchContacts(context)

        val contactsList:MutableList<Contact> = mutableListOf()

        for ((index,contact) in contactNames.withIndex()){
            contactsList.add(Contact(index, contact, "default", Uri.EMPTY))
        }

        ContactModel.contacts = contactsList
        ChatModel.setUpChat(contactsList,false)
        MessageModel.setUpMessages()
        Log.i("____Contacts", ContactModel.contacts.toString())
        Log.i("____Chats", ChatModel.chats.toString())
        Log.i("____Messages", MessageModel.messagesList.toString())
    }
}