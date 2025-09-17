package com.example.myapplication1

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ContactsList.newInstance] factory method to
 * create an instance of this fragment.
 */
class ContactsList : Fragment(),  ContactsAdapter.OnItemClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    val contactList = mutableSetOf<Contact>()

    val visitedContacts = mutableSetOf<String>()

    lateinit var numberOfContacts : TextView


    lateinit var back_btn: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_contacts_list, container, false)

        numberOfContacts = view.findViewById<TextView>(R.id.number_of_contacts)


        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.READ_CONTACTS),
            0
        )

        if(checkContactsPermission()){
            val projection = arrayOf(
                ContactsContract.CommonDataKinds.Phone._ID,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
//        ContactsContract.CommonDataKinds.Phone.IN_DEFAULT_DIRECTORY,
//        ContactsContract.CommonDataKinds.Phone.IS_USER_PROFILE,
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Phone.PHOTO_URI
            )


            val sortOrder = "${ContactsContract.Contacts.DISPLAY_NAME_PRIMARY} ASC"

            Log.d("ABCD 2", "onCreate")

            requireActivity().contentResolver.query(
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

                    Log.d("ABCD", "$id $name")

                    if (visitedContacts.contains(phone)) continue
                    visitedContacts.add(phone)
                    val photoString = cursor.getString(photoUri)
                    val photo = photoString?.toUri()

                    contactList.add(Contact(
                        id,
                        null,
                        photo.toString(),
                        name?:"unknown",
                        phone?:"unknown"))

                    // images.add(Image(id,name,uri))
                }
                numberOfContacts.text = if(contactList.isNotEmpty())"${contactList.size} Contacts" else "No Contacts"
                Log.i("unique",contactList.toString())
                print(contactList.toString())
            }
        }


        val adapter = ContactsAdapter(contactList)
        val myRecycler = view.findViewById<RecyclerView>(R.id.recyclerView)
        myRecycler.adapter = adapter
        myRecycler.layoutManager = LinearLayoutManager(requireContext())


        back_btn = view.findViewById<ImageView>(R.id.go_back)





        adapter.setOnclickListener(this)
        back_btn.setOnClickListener {
            val main_chats = MainChats()
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.main, main_chats);
                addToBackStack("homeFragment")
                setReorderingAllowed(true)
                commit()
            }
        }



        return view
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ContactsList.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ContactsList().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    val args = Bundle()
    override fun onItemClick(position: Int) {
        val messagesList = mutableListOf<Message>(
            Message(contactList.elementAt(0).id,null, null, "How are you?", "received", ReadStatus.READ),
            Message(contactList.elementAt(0).id,"John Adams", "How are you?", "I am fine. How are you?", "sent",
                ReadStatus.READ),
            Message(contactList.elementAt(0).id,"You", "I am fine. How are you?", "I am good.",
                readStatus =ReadStatus.READ),
            Message(contactList.elementAt(0).id,null, null, "How was your stay at the hotel?", "received",
                ReadStatus.READ),
            Message(contactList.elementAt(0).id,"John Adams", "How was your stay at the hotel?", "It was fine, it ain't much to talk about tho, I've been staying in a 4-start hotel and they're hospitable", "sent",
                ReadStatus.READ),
            Message(contactList.elementAt(0).id,"John Adams", "How was your stay at the hotel?", "It was fine, it ain't much to talk about tho, I've been staying in a 4-start hotel and they're hospitable", "received",
                ReadStatus.UNREAD),
            Message(contactList.elementAt(0).id,null, null, "It was fine, it ain't much to talk about tho, I've been staying in a 4-start hotel and they're hospitable", "received",
                ReadStatus.UNREAD)
        )

        contactList.elementAt(0).apply {
            this.messages = messagesList
        }

// neo graph  nav
        args.apply {
            putSerializable("contact",contactList.elementAt(position))
        }

        val contactMessages = ContactMessages().apply{
            arguments = args
        }

        Toast.makeText(requireContext(),contactList.elementAt(0).messages?.size.toString(), Toast.LENGTH_LONG).show()

        parentFragmentManager.beginTransaction().apply {
            replace(R.id.main, contactMessages);
            setReorderingAllowed(true)
            commit()
        }
    }


    fun checkContactsPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.READ_CONTACTS
        ) == PackageManager.PERMISSION_GRANTED
    }
}