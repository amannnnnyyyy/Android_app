package com.example.myapplication1
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ContactMessages : Fragment() {
    private val REQUEST_CALL_PERMISSION = 101

    private val requestCallPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            makePhoneCall()
        } else {
            TODO()
        }
    }


    lateinit var contactHeader: RelativeLayout
    lateinit var username: TextView
    lateinit var profilePic: ImageView
    lateinit var description: TextView
    lateinit var audio_call_btn: ImageView
    lateinit var go_back: ImageView

    var contact: Contact? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_contact_messages, container, false)


        username = view.findViewById<TextView>(R.id.userName)
        profilePic = view.findViewById<ImageView>(R.id.userProfile)
        description = view.findViewById<TextView>(R.id.userDescription)
        audio_call_btn = view.findViewById<ImageView>(R.id.audio_call)

        go_back = view.findViewById<ImageView>(R.id.go_back)

        arguments?.let{
            contact = it.getSerializable("contact", Contact::class.java)

            username.text = contact?.name?:username.text
            profilePic.setImageURI(contact?.profilePicture?.toUri())
            description.text = contact?.messageDescription?:description.text
        }



        val adapter = MessageAdapter(contact?.messages)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewMessages)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(view.context)

        return view
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)






        contactHeader = view.findViewById<RelativeLayout>(R.id.header_content)
        contactHeader.setOnClickListener {
            Log.i("toChat",contact.toString())
            if(contact!=null){
//                Intent(view.context, ChatAppClone::class.java).also{
//                    it.putExtra("Person",contact)
//                    startActivity(it)
//                }
                parentFragmentManager
                    .beginTransaction()
                    .replace(R.id.mainHolder, ContactDetails())
                    .addToBackStack(null)
                    .commit()
            }
        }


        go_back.setOnClickListener {
//            parentFragmentManager.beginTransaction().remove(this).commit()
            findNavController().navigateUp()
           // parentFragmentManager.beginTransaction().replace(R.id.mainHolder, MainChats()).commit()
        }

//    requestPermissions(
//
//    )



        audio_call_btn.setOnClickListener {
            initiateCallAction()
        }




    }

    fun initiateCallAction() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            makePhoneCall()
        } else {
            requestCallPermissionLauncher.launch(Manifest.permission.CALL_PHONE)
        }
    }

    private fun makePhoneCall() {
        val intent = Intent(Intent.ACTION_CALL).apply {
            data = "tel:${description.text}".toUri()
        }
        startActivity(intent)
    }


}