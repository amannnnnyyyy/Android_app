package com.example.myapplication1

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView

class ContactsAdapter(
    val contacts: Set<Contact>
): RecyclerView.Adapter<ContactsAdapter.ContactViewHolder>() {
    private var onClickListener: OnItemClickListener? = null


    lateinit var context: Context

    fun interface OnItemClickListener {
        fun onItemClick(position: Int,type:String,view: View)
    }

    fun setOnclickListener(listener: OnItemClickListener) {
        this.onClickListener = listener
    }

    inner class ContactViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ContactViewHolder {
        context = parent.context
        val inflater = LayoutInflater.from(
            parent.context)

        val view =  inflater.inflate(
            R.layout.contacts,
            parent, false)

        return ContactViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ContactViewHolder,
        position: Int
    ) {
        if(position==0){
//            holder.itemView.apply {
//                val userProfile = findViewById<ImageView>(R.id.userProfile)
//                userProfile.setImageResource(contacts[position].profilePicture)
//            }
            Toast.makeText(context,"This is a setup $contacts", Toast.LENGTH_LONG).show()
        }
        val item = contacts.toMutableList()[position]
        val userProfile: ShapeableImageView
        holder.itemView.apply{
            userProfile = findViewById<ShapeableImageView>(R.id.userProfile)
            val name = findViewById<TextView>(R.id.name)
            val description = findViewById<TextView>(R.id.description)

            userProfile.setImageURI(contacts.toMutableList()[position].profilePicture.toUri())
            name.text = contacts.toMutableList()[position].name
                description.text = contacts.toMutableList()[position].phoneNumber
            Log.i("context_custom","$context value")
        }
        val transitionName = "profileImage_$position"
        userProfile.transitionName = transitionName

        userProfile.setOnClickListener {
            onClickListener?.onItemClick(position,"dialog", userProfile)
        }

        holder.itemView.setOnClickListener {
            onClickListener?.onItemClick(position,"normal", userProfile)
        }
    }

    override fun getItemCount(): Int {
        return contacts.size
    }

}