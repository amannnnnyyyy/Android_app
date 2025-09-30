package com.example.myapplication1.view.adapters.recycler_view_adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication1.R
import com.example.myapplication1.core.model.contact.Contact

class ContactsListRecyclerViewAdapter(contacts:List<Contact>,  private val onUpdateChange: (ContactClicked)-> Unit): RecyclerView.Adapter<ContactsListRecyclerViewAdapter.ContactsListViewHolder>() {
    private val contactList = contacts
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ContactsListViewHolder {
        return ContactsListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.contacts, parent, false))
    }

    override fun onBindViewHolder(
        holder: ContactsListViewHolder,
        position: Int
    ) {
        val contact = contactList[position]
        holder.profileImage.setImageURI(contact.profilePic)
        holder.name.text = contact.name
        holder.phoneNumber.text = contact.phoneNumber

        holder.itemView.setOnClickListener {
            onUpdateChange.invoke(ContactClicked.ItemClick(0,contact.contactId))
        }

        holder.profileImage.setOnClickListener {
            onUpdateChange.invoke(ContactClicked.ProfileClick(contact.contactId))
        }
    }

    override fun getItemCount(): Int  = contactList.size

    inner class ContactsListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val profileImage: ImageView = itemView.findViewById<ImageView>(R.id.userProfile)
        val name: TextView = itemView.findViewById<TextView>(R.id.name)
        val phoneNumber: TextView = itemView.findViewById<TextView>(R.id.description)

    }
}

sealed class ContactClicked{
    class ItemClick(val chatId: Int, val contactId: Int):ContactClicked()
    class ProfileClick(val contactId:Int): ContactClicked()
}