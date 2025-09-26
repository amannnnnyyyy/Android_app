package com.example.myapplication1.view.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication1.R
import com.example.myapplication1.core.model.chat.ChatModel
import com.example.myapplication1.core.model.contact.ContactModel
import com.example.myapplication1.core.model.message.MessageModel
import com.example.myapplication1.view.fragments.chat_list.ChatListViewModel

class MyChatActivity : AppCompatActivity() {

    val viewModel by viewModels<MyChatViewModel>()
    //val chatListViewModel: ChatListViewModel by viewModels()

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                fetchContacts()
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
            fetchContacts()
            Log.i("-----Contacts", ContactModel.contacts.toString())
        } else {
            requestPermissionLauncher.launch(Manifest.permission.READ_CONTACTS)
        }

        viewModel.contact.observe(this) {

        }
    }

    fun fetchContacts(){
        viewModel.contact.observe(this) { contacts ->
            Log.i("forchat",contacts.toString())

            if (ChatModel.chats.isEmpty()){
               // chatListViewModel.setUpChat(contacts,true)
            }
            Log.i("found_chats","${contacts.size} ${contacts.filter { it.name=="Yadasa" }.size}")
        }
//        chatListViewModel.chats.observe(this){
//            Log.i("index-inside-chat", "from activity ${it}]")
//
//        }
    }
}