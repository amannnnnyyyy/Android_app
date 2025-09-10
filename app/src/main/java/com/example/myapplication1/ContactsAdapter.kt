package com.example.myapplication1

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ContactsAdapter(
    val contacts: List<Contact>
): RecyclerView.Adapter<ContactsAdapter.ContactViewHolder>() {
    private var onClickListener: OnItemClickListener? = null


    lateinit var context: Context

    fun interface OnItemClickListener {
        fun onItemClick(position: Int, model:Contact)
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
        val view = LayoutInflater.from(
            parent.context).inflate(
            R.layout.contacts,
            parent, false)

        return ContactViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ContactViewHolder,
        position: Int
    ) {
        val item = contacts[position]
        holder.itemView.apply{
            val userProfile = findViewById<ImageView>(R.id.userProfile)
            val name = findViewById<TextView>(R.id.name)
            val description = findViewById<TextView>(R.id.description)

            userProfile.setImageResource(contacts[position].profilePicture)
            name.text = contacts[position].name
            description.text = contacts[position].messageDescription
            Log.i("context_custom","$context value")
        }
        holder.itemView.setOnClickListener {
            onClickListener?.onItemClick(position, item)
//            Intent(context, ChatAppClone::class.java).also{
//                it.putExtra("person",contacts[position])
//                context.startActivity(it)
//            }
        }
    }

    override fun getItemCount(): Int {
        return contacts.size
    }

}