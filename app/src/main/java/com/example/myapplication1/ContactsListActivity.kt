package com.example.myapplication1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ContactsListActivity : AppCompatActivity(), ContactsAdapter.OnItemClickListener {
    val contactList = mutableListOf(
        Contact(R.drawable.audio_call, "John", "David"),
        Contact(R.drawable.profile_picture, "Jackson", "David"),
        Contact(R.drawable.chat_lock_icon, "Frontier", "David"),
        Contact(R.drawable.profile_picture, "Dan", "David"),
        Contact(R.drawable.audio_call, "Browser", "David"),
        Contact(R.drawable.disappearing_messages, "Fredrick", "David"),
        Contact(R.drawable.chat_lock_icon, "Hansel", "David"),
        Contact(R.drawable.profile_picture, "Gretel", "David"),
        Contact(R.drawable.baseline_notifications_none_24, "Smith", "David"),
        Contact(R.drawable.profile_picture, "Truncate", "David"),
        Contact(R.drawable.calculator, "Dover", "David"),
        Contact(R.drawable.profile_picture, "Groove", "David"),
        Contact(R.drawable.disappearing_messages, "Stinson", "David"),

        )

    lateinit var back_btn: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_contacts_list)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }



        val adapter = ContactsAdapter(contactList)
        val myRecycler = findViewById<RecyclerView>(R.id.recyclerView)
        myRecycler.adapter = adapter
        myRecycler.layoutManager = LinearLayoutManager(this)


        val testData  = Contact(R.drawable.profile_picture, "John", "David")
        back_btn = findViewById<ImageView>(R.id.go_back)

        adapter.setOnclickListener(this)
        back_btn.setOnClickListener {
            Intent(this, ChatAppClone::class.java).also{
                it.putExtra("Person", testData)
                startActivity(it)
            }
        }
    }

    val contactMessages = ContactMessages()
    override fun onItemClick(position: Int) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameLayout, contactMessages);
            addToBackStack("homeFragment")
            setReorderingAllowed(true)
            commit()
        }
//        Intent(this, ChatAppClone::class.java).also{
//            it.putExtra("Person", contactList[position])
//            startActivity(it)
//        }
    }
}