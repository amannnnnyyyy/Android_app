package com.example.myapplication1

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
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
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDate
import kotlin.random.Random

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ContactsList : Fragment(),  ContactsAdapter.OnItemClickListener {
    @RequiresApi(Build.VERSION_CODES.O)
    private val chatList: List<Chats> = listOf(
        Chats(
            Contact(
                1,
                mutableListOf(
                    Message(1, null, null, "Hello", "received", ReadStatus.UNREAD),
                    Message(1, null, null, "Hey there", "received", ReadStatus.UNREAD)
                ),
                Uri.EMPTY.toString(),
                name = "john",
                messageDescription = "New User",
                phoneNumber = "+9719782367986"
            ),
            phoneNumber = null,
            status = ChatSeenStatus.Unread,
            timeSent = LocalDate.now()
        ),
        Chats(
            Contact(
                1,
                mutableListOf(
                    Message(1,null,null,"Hello","received", ReadStatus.UNREAD),
                    Message(1,null,null,"Hey there","received", ReadStatus.UNREAD)
                ),
                Uri.EMPTY.toString(),
                name = "jack",
                messageDescription = "New User",
                phoneNumber = "+9719782000086"
            ),
            phoneNumber = null,
            status = ChatSeenStatus.Unread,
            timeSent = LocalDate.now()
        ),
        Chats(
            Contact(
                1,
                mutableListOf(
                    Message(1,null,null,"Hello","received", ReadStatus.UNREAD),
                    Message(1,null,null,"Hey there","received", ReadStatus.UNREAD)
                ),
                Uri.EMPTY.toString(),
                name = "smith",
                messageDescription = "New User",
                phoneNumber = "+9719787777986"
            ),
            phoneNumber = null,
            status = ChatSeenStatus.Unread,
            timeSent = LocalDate.now()
        ),
        Chats(
            Contact(
                1,
                mutableListOf(
                    Message(1,null,null,"Hello","received", ReadStatus.UNREAD),
                    Message(1,null,null,"Hey there","received", ReadStatus.UNREAD)
                ),
                Uri.EMPTY.toString(),
                name = "jason",
                messageDescription = "New User",
                phoneNumber = "+97197823968858"
            ),
            phoneNumber = null,
            status = ChatSeenStatus.Unread,
            timeSent = LocalDate.now()
        ),
        Chats(
            Contact(
                1,
                mutableListOf(
                    Message(1,null,null,"Hello","received", ReadStatus.UNREAD),
                    Message(1,null,null,"Hey there","received", ReadStatus.UNREAD)
                ),
                Uri.EMPTY.toString(),
                name = "john",
                messageDescription = "New User",
                phoneNumber = "+9719782333333"
            ),
            phoneNumber = null,
            status = ChatSeenStatus.Unread,
            timeSent = LocalDate.now()
        ),
        Chats(
            Contact(
                1,
                mutableListOf(
                    Message(1,null,null,"Hello","received", ReadStatus.UNREAD),
                    Message(1,null,null,"Hey there","received", ReadStatus.UNREAD)
                ),
                Uri.EMPTY.toString(),
                name = "drago",
                messageDescription = "New User",
                phoneNumber = "+9719782888888"
            ),
            phoneNumber = null,
            status = ChatSeenStatus.Unread,
            timeSent = LocalDate.now()
        ),
        Chats(
            Contact(
                1,
                mutableListOf(
                    Message(1,null,null,"Hello","received", ReadStatus.UNREAD),
                    Message(1,null,null,"Hey there","received", ReadStatus.UNREAD)
                ),
                Uri.EMPTY.toString(),
                name = "felix",
                messageDescription = "New User",
                phoneNumber = "+9719782367111"
            ),
            phoneNumber = null,
            status = ChatSeenStatus.Unread,
            timeSent = LocalDate.now()
        ),
        Chats(
            Contact(
                1,
                mutableListOf(
                    Message(1,null,null,"Hello","received", ReadStatus.UNREAD),
                    Message(1,null,null,"Hey there","received", ReadStatus.UNREAD)
                ),
                Uri.EMPTY.toString(),
                name = "clarence",
                messageDescription = "New User",
                phoneNumber = "+9719782365555"
            ),
            phoneNumber = null,
            status = ChatSeenStatus.Unread,
            timeSent = LocalDate.now()
        ),
        Chats(
            Contact(
                1,
                mutableListOf(
                    Message(1,null,null,"Hello","received", ReadStatus.UNREAD),
                    Message(1,null,null,"Hey there","received", ReadStatus.UNREAD)
                ),
                Uri.EMPTY.toString(),
                name = "florence",
                messageDescription = "New User",
                phoneNumber = "+9719782367777"
            ),
            phoneNumber = null,
            status = ChatSeenStatus.Unread,
            timeSent = LocalDate.now()
        ),
        Chats(
            Contact(
                1,
                mutableListOf(
                    Message(1,null,null,"Hello","received", ReadStatus.UNREAD),
                    Message(1,null,null,"Hey there","received", ReadStatus.UNREAD)
                ),
                Uri.EMPTY.toString(),
                name = "trevor",
                messageDescription = "New User",
                phoneNumber = "+971978565565466"
            ),
            phoneNumber = null,
            status = ChatSeenStatus.Unread,
            timeSent = LocalDate.now()
        ),
        Chats(
            Contact(
                1,
                mutableListOf(
                    Message(1,null,null,"Hello","received", ReadStatus.UNREAD),
                    Message(1,null,null,"Hey there","received", ReadStatus.UNREAD)
                ),
                Uri.EMPTY.toString(),
                name = "noah",
                messageDescription = "New User",
                phoneNumber = "+9719782344444"
            ),
            phoneNumber = null,
            status = ChatSeenStatus.Unread,
            timeSent = LocalDate.now()
        )
    )
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
        val view =  inflater.inflate(R.layout.fragment_contacts_list_old, container, false)

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

                    val contact = Contact(
                        id,
                        null,
                        photo.toString(),
                        name ?: "unknown",
                        phone ?: "unknown",
                        phoneNumber = phone.toString()
                    )
                    contactList.add(contact)
                    Log.i("phoneNumber",contact.phoneNumber)

                    findContactForChat(contact = contact)

                    // images.add(Image(id,name,uri))
                }
                numberOfContacts.text = if(contactList.isNotEmpty())"${contactList.size} Contacts" else "No Contacts"
                Log.i("unique",contactList.toString())
                print(contactList.toString())
            }
        }


        val adapter = ContactsAdapter(chatList.map {
            it.sender
        }.toSet() as Set<Contact>)
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
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onItemClick(position: Int, type:String, profilePic: View) {
        if (type=="normal"){
            val messagesList = MessagesData().getMessageForContact(contactList.elementAt(position).id,contactList.elementAt(position).name)

            contactList.elementAt(0).apply {
                this.messages = messagesList
            }


            val extras: FragmentNavigator.Extras

            val nav = requireParentFragment().findNavController()
//            val action = ContactsListDirections.actionContactsList2ToContactMessages(chatList[position])
//
//            if(profilePic!=null){
//
//                extras = FragmentNavigatorExtras(
//                    profilePic to "profile_pic"
//                )
//                nav.navigate(action,extras)
//
//            }


        }
        else if(type=="dialog"){
            val extras: FragmentNavigator.Extras

            val nav = requireParentFragment().findNavController()
            val con = chatList.elementAt(position).sender ?:contactList.elementAt(position)
//            val action = ContactsListDirections.actionContactsList2ToProfileDialog("contactsList",con)
//
//
//            if(profilePic!=null){
//
//                extras = FragmentNavigatorExtras(
//                    profilePic to "profile_pic"
//                )
//                nav.navigate(action,extras)
//
//            }

//            profileDialog.arguments =bundle
//            parentFragmentManager
//                .beginTransaction()
//                .add(profileDialog,"profile_detail")
//                .commit()
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun findContactForChat(max:Int = chatList.size, contact: Contact){
        val randomIndex = Random.nextInt(0,max)
        chatList[randomIndex].sender = contact
    }


    fun checkContactsPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.READ_CONTACTS
        ) == PackageManager.PERMISSION_GRANTED
    }
}