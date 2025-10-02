package com.example.myapplication1

import android.app.Dialog
import androidx.fragment.app.Fragment
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.compose.animation.core.Transition
import androidx.core.net.toUri
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater

class ProfileDialog: DialogFragment(R.layout.profile_dialog_fragment) {
    var contact: Contact? = null
    private lateinit var userProfileField:ImageView

    private val dialogArgs: ProfileDialogArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userProfileField = view.findViewById<ImageView>(R.id.userProfile)

        sharedElementEnterTransition = TransitionInflater.from(requireContext()).inflateTransition(
            android.R.transition.move
        )
        sharedElementReturnTransition = TransitionInflater.from(requireContext()).inflateTransition(
            android.R.transition.move
        )

        val name = dialogArgs.contact.name
        val sender = dialogArgs.sender


        val nameField = view.findViewById<TextView>(R.id.name)
        val messages = view.findViewById< ImageView>(R.id.messages)


        nameField.text = name

        if(sender=="contactsList") {
            contact = dialogArgs.contact
            Log.i("profile","${contact?.profilePicture=="null"}")
            if(contact?.profilePicture!="null")
                userProfileField.setImageURI(contact?.profilePicture?.toUri())
            else
                userProfileField.setImageResource(R.drawable.profile)

            val args = Bundle()
            args.apply {
                putSerializable("contact",contact)
            }

            messages.setOnClickListener {
                dismiss()
                Log.i("going","error here?")
//                parentFragment?.parentFragmentManager?.beginTransaction()
//                    ?.replace(R.id.mainHolder, contactMessages)
//                    ?.addToBackStack(null)
//                    ?.commit()
            }
        }
        else
            userProfileField.setImageResource(R.drawable.profile)

    }
}