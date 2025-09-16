package com.example.myapplication1

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.Image
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.jar.Manifest

class ContactMessages : Fragment() {

    lateinit var contactHeader: RelativeLayout
    lateinit var username: TextView
    lateinit var profilePic: ImageView
    lateinit var description: TextView
    lateinit var audio_call_btn: ImageView
    lateinit var go_back: ImageView

    var contact: Contact? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_contact_messages, container, false)


        username = view.findViewById<TextView>(R.id.userName)
        profilePic = view.findViewById<ImageView>(R.id.userProfile)
        description = view.findViewById<TextView>(R.id.userDescription)
        audio_call_btn = view.findViewById<ImageView>(R.id.audio_call)

        go_back = view.findViewById<ImageView>(R.id.go_back)

        arguments?.let{
            contact = it.getSerializable("contact") as? Contact

            username.text = contact?.name?:username.text
            profilePic.setImageURI(contact?.profilePicture?.toUri())
            description.text = contact?.messageDescription?:description.text
        }



        val adapter = MessageAdapter(contact?.messages)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewMessages)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(view.context)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)






        contactHeader = view.findViewById<RelativeLayout>(R.id.header_content)
        contactHeader.setOnClickListener {
            Log.i("toChat",contact.toString())
            if(contact!=null){
                Intent(view.context, ChatAppClone::class.java).also{
                    it.putExtra("Person",contact)
                    startActivity(it)
                }
            }
        }


        go_back.setOnClickListener {
//            parentFragmentManager.beginTransaction().remove(this).commit()
            parentFragmentManager.beginTransaction().replace(R.id.main, MainChats()).commit()
        }

//    requestPermissions(
//
//    )
        audio_call_btn.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = "tel:${description.text}".toUri()
            }
            startActivity(intent)
        }


    }

}