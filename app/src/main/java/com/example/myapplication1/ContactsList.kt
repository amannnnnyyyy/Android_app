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
import androidx.navigation.fragment.findNavController
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
            findNavController().navigateUp()
//            parentFragmentManager.beginTransaction().apply {
//                replace(R.id.mainHolder, main_chats);
//                addToBackStack("homeFragment")
//                setReorderingAllowed(true)
//                commit()
//            }
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
    override fun onItemClick(position: Int,type:String) {
        if (type=="normal"){
            val messagesList = MessagesData().getMessageForContact(contactList.elementAt(position).id,contactList.elementAt(position).name)

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

            val nav = requireParentFragment().findNavController()
            val action = ContactsListDirections.actionContactsList2ToContactMessages()
            nav.navigate(action)
//            parentFragmentManager.beginTransaction().apply {
//                replace(R.id.mainHolder, contactMessages);
//                setReorderingAllowed(true)
//                commit()
//            }

        }
        else if(type=="dialog"){
            val profileDialog = ProfileDialog()
            val bundle = Bundle().apply {
                putString("name",contactList.elementAt(position).name)
                putString("sender","contactsList")
                putSerializable("Contact",contactList.elementAt(position))
            }

            profileDialog.arguments =bundle
            parentFragmentManager
                .beginTransaction()
                .add(profileDialog,"profile_detail")
                .commit()
            //profileDialog.show(childFragmentManager,"profile dialog")

            //ContactsListDirections.actionContactsListToContactDetails2("1", "asd")
        }
    }


    fun checkContactsPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.READ_CONTACTS
        ) == PackageManager.PERMISSION_GRANTED
    }
}