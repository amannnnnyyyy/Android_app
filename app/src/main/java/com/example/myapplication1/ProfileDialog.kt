package com.example.myapplication1

import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.net.toUri
import androidx.fragment.app.DialogFragment

class ProfileDialog: DialogFragment() {
    var contact: Contact? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity(), R.style.DialogAnimation)
        val view = layoutInflater.inflate(R.layout.profile_dialog_fragment, null)
        builder.setView(view)

        val name = arguments?.getString("name")
        val sender = arguments?.getString("sender")

        val nameField = view.findViewById<TextView>(R.id.name)
        val userProfileField = view.findViewById<ImageView>(R.id.userProfile)

        nameField.text = name

        if(sender=="contactsList") {
            arguments?.let{
                contact = arguments?.getSerializable("Contact",Contact::class.java)
            }
            Log.i("profile","${contact?.profilePicture=="null"}")
            if(contact?.profilePicture!="null")
                userProfileField.setImageURI(contact?.profilePicture?.toUri())
            else
                userProfileField.setImageResource(R.drawable.profile)
        }
        else
            userProfileField.setImageResource(R.drawable.profile)


        return builder.create()
    }
}