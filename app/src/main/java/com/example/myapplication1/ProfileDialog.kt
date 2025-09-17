package com.example.myapplication1

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class ProfileDialog: DialogFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity(), R.style.DialogAnimation)
        val view = layoutInflater.inflate(R.layout.profile_dialog_fragment, null)
        builder.setView(view)

        val name = arguments?.getString("name")
        val person = arguments?.getSerializable("Contact")

        val nameField = view.findViewById<TextView>(R.id.name)
        val userProfileField = view.findViewById<ImageView>(R.id.userProfile)

        nameField.text = name
        userProfileField.setImageResource(R.drawable.profile_picture)

        return builder.create()
    }
}