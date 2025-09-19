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
import androidx.transition.TransitionInflater

class ProfileDialog: DialogFragment() {
    var contact: Contact? = null
    private lateinit var transitionName:String
    private lateinit var userProfileField:ImageView

    private  var pivotX: Float? = null

    private  var pivotY: Float? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        transitionName = arguments?.getString("transitionName").toString()

        userProfileField = view.findViewById<ImageView>(R.id.userProfile)
        userProfileField.transitionName = transitionName

//        sharedElementEnterTransition = TransitionInflater.from(requireContext()).inflateTransition(
//            android.R.transition.move
//        )
//        sharedElementReturnTransition = TransitionInflater.from(requireContext()).inflateTransition(
//            android.R.transition.move
//        )

        val name = arguments?.getString("name")
        val sender = arguments?.getString("sender")


        val nameField = view.findViewById<TextView>(R.id.name)
        val messages = view.findViewById< ImageView>(R.id.messages)
        val detail = view.findViewById<ImageView>(R.id.detail)


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

            val args = Bundle()
            args.apply {
                putSerializable("contact",contact)
            }

            val contactMessages = ContactMessages().apply{
                arguments = args
            }

            messages.setOnClickListener {
                dismiss()
                Log.i("going","error here?")
                parentFragment?.parentFragmentManager?.beginTransaction()
                    ?.replace(R.id.mainHolder, contactMessages)
                    ?.addToBackStack(null)
                    ?.commit()
            }
        }
        else
            userProfileField.setImageResource(R.drawable.profile)

    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity(), R.style.DialogAnimation)
        val view = layoutInflater.inflate(R.layout.profile_dialog_fragment, null)
        builder.setView(view)



        transitionName = arguments?.getString("transitionName").toString()

        userProfileField = view.findViewById<ImageView>(R.id.userProfile)
        userProfileField.transitionName = transitionName

//        sharedElementEnterTransition = TransitionInflater.from(requireContext()).inflateTransition(
//            android.R.transition.move
//        )
//        sharedElementReturnTransition = TransitionInflater.from(requireContext()).inflateTransition(
//            android.R.transition.move
//        )

        val name = arguments?.getString("name")
        val sender = arguments?.getString("sender")

        val nameField = view.findViewById<TextView>(R.id.name)
        val messages = view.findViewById< ImageView>(R.id.messages)
        val detail = view.findViewById<ImageView>(R.id.detail)


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

            val args = Bundle()
            args.apply {
                putSerializable("contact",contact)
            }

            val contactMessages = ContactMessages().apply{
                arguments = args
            }

            messages.setOnClickListener {
                dismiss()
                Log.i("going","error here?")
                parentFragment?.parentFragmentManager?.beginTransaction()
                    ?.replace(R.id.mainHolder, contactMessages)
                    ?.addToBackStack(null)
                    ?.commit()
            }
        }
        else
            userProfileField.setImageResource(R.drawable.profile)


        return builder.create()
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(requireContext(),"Destroying $transitionName", Toast.LENGTH_SHORT).show()
    }
}