package com.example.myapplication1

import android.Manifest
import android.content.ContentUris
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.net.toFile
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.Calendar
import androidx.core.net.toUri

class ContactsListActivity : AppCompatActivity(), ContactsAdapter.OnItemClickListener {
    val contactList = mutableListOf<Contact>()


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



        //////////////////////////////////////////////////////////////////////////

        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.READ_CONTACTS),
            0
        )



        val projection = arrayOf(
            ContactsContract.CommonDataKinds.Phone._ID,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.CommonDataKinds.Phone.PHOTO_URI
        )


        val sortOrder = "${ContactsContract.Contacts.DISPLAY_NAME_PRIMARY} ASC"

        contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            projection,
            null,
            null,
            sortOrder
        )?.use {cursor ->
            val idColumn = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID)
            val nameColumn = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_PRIMARY)
            val phoneNumber = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
            val photoUri = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI)

            while (cursor.moveToNext()){
                val id = cursor.getLong(idColumn)
                val name = cursor.getString(nameColumn)
                val phone = cursor.getString(phoneNumber)
                val photoString = cursor.getString(photoUri)
                val photo = photoString?.toUri()

                contactList.add(Contact(
                    id,null,
                    photo.toString(),
                    name?:"unknown",
                    phone?:"unknown"))
               // images.add(Image(id,name,uri))
            }
            Log.i("unique",contactList.toString())
            print(contactList.toString())
        }
//////////////////////////////////////////////////////////////////////////////////////////////////


        val adapter = ContactsAdapter(contactList)
        val myRecycler = findViewById<RecyclerView>(R.id.recyclerView)
        myRecycler.adapter = adapter
        myRecycler.layoutManager = LinearLayoutManager(this)


        back_btn = findViewById<ImageView>(R.id.go_back)




        
        adapter.setOnclickListener(this)
//        back_btn.setOnClickListener {
//            Intent(this, ChatAppClone::class.java).also{
//                it.putExtra("Person", testData)
//                startActivity(it)
//            }
//        }
    }


    val args = Bundle()
    override fun onItemClick(position: Int) {
        val messagesList = mutableListOf<Message>(
            Message(contactList?.get(0)?.id,null, null, "How are you?", "received"),
            Message(contactList[0].id,"John Adams", "How are you?", "I am fine. How are you?", "sent"),
            Message(contactList[0].id,"You", "I am fine. How are you?", "I am good."),
            Message(contactList[0].id,null, null, "How was your stay at the hotel?", "received"),
            Message(contactList[0].id,"John Adams", "How was your stay at the hotel?", "It was fine, it ain't much to talk about tho, I've been staying in a 4-start hotel and they're hospitable", "sent"),
            Message(contactList[0].id,"John Adams", "How was your stay at the hotel?", "It was fine, it ain't much to talk about tho, I've been staying in a 4-start hotel and they're hospitable", "received"),
            Message(contactList[0].id,"John Adams", "How was your stay at the hotel?", "It was fine, it ain't much to talk about tho, I've been staying in a 4-start hotel and they're hospitable", "sent")
        )

            contactList[0].apply {
                this.messages = messagesList
            }


        args.apply {
            putSerializable("contact",contactList[position])
        }

        val contactMessages = ContactMessages().apply{
            arguments = args
    }

        Toast.makeText(this,contactList[0].messages?.size.toString(), Toast.LENGTH_LONG).show()

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.main, contactMessages);
            addToBackStack("homeFragment")
            setReorderingAllowed(true)
            commit()
        }
    }
}
