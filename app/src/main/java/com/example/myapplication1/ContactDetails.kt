package com.example.myapplication1

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat
import androidx.core.net.toUri
import androidx.navigation.fragment.navArgs

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ContactDetails.newInstance] factory method to
 * create an instance of this fragment.
 */
class ContactDetails : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var go_back_btn: ImageView
    lateinit var more_options: ImageView
    lateinit var user_profile: ImageView
    lateinit var profile_name: TextView
    lateinit var user_phone: TextView
    lateinit var last_seen: TextView
    lateinit var audio_call: LinearLayout
    lateinit var video_call: LinearLayout
    lateinit var search: LinearLayout
    lateinit var notification_layout: LinearLayout
    lateinit var media_layout: LinearLayout
    lateinit var encryption_layout: RelativeLayout
    lateinit var disappearing_layout: RelativeLayout
    lateinit var chat_lock: RelativeLayout
    lateinit var toggle: SwitchCompat
    lateinit var chat_privacy_layout: RelativeLayout
    lateinit var create_group_layout: RelativeLayout
    lateinit var add_to_favourites_layout: RelativeLayout
    lateinit var add_to_list_layout: RelativeLayout
    lateinit var block_layout: RelativeLayout
    lateinit var report_layout: RelativeLayout

    private val navArgs by navArgs<ContactDetailsArgs>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_contact_details, container, false)



        go_back_btn = view.findViewById<ImageView>(R.id.go_back)
        more_options = view.findViewById(R.id.more_options)
        user_profile = view.findViewById(R.id.userProfile)
        profile_name = view.findViewById(R.id.profile_name)
        user_phone = view.findViewById(R.id.userPhone)
        last_seen = view.findViewById(R.id.lastSeen)
        audio_call = view.findViewById(R.id.audio_call)
        video_call = view.findViewById(R.id.video_call)
        search = view.findViewById(R.id.search)
        notification_layout = view.findViewById(R.id.notification_layout)
        media_layout = view.findViewById(R.id.media_layout)
        encryption_layout = view.findViewById(R.id.encryption_layout)
        disappearing_layout = view.findViewById(R.id.disappear_layout)
        chat_lock = view.findViewById(R.id.chatLockGroup)
        toggle = view.findViewById<SwitchCompat>(R.id.toggle)
        chat_privacy_layout = view.findViewById(R.id.chat_privacy_layout)
        create_group_layout = view.findViewById(R.id.create_group_layout)
        add_to_favourites_layout = view.findViewById(R.id.add_to_favourites_layout)
        add_to_list_layout = view.findViewById(R.id.add_to_list_layout)
        block_layout = view.findViewById(R.id.block_layout)
        report_layout = view.findViewById(R.id.report_layout)

        var contact: Contact? = null

        arguments?.let{
            contact = it.getSerializable("contact") as? Contact
        }

        Log.i("profile",contact.toString())
        user_profile.setImageURI(contact?.profilePicture?.toUri())

        profile_name.text = contact?.name



        setupClickListeners(
            context = requireContext(), listOf(
                go_back_btn to "Going back",
                more_options to "showing more options",
                user_profile to "this is profile of ${profile_name.text}",
                profile_name to "This is name of user",
                user_phone to "This is user's phone",
                last_seen to "user is last seen at ${last_seen.text}",
                audio_call to "audio calling... ${profile_name.text}",
                video_call to "video calling... ${profile_name.text}",
                search to "Search",
                notification_layout to "Notification",
                media_layout to "Media",
                encryption_layout to "Encryption",
                disappearing_layout to "Disappearing messages",
                chat_lock to "chat lock",
                toggle to "Toggling",
                chat_privacy_layout to "chat privacy",
                create_group_layout to "creating group",
                add_to_favourites_layout to "adding ${profile_name.text} to favourites",
                add_to_list_layout to "Adding ${profile_name.text} to list",
                block_layout to "Blocking ${profile_name.text}",
                report_layout to "Reporting ${profile_name.text}"
            )
        )
    return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ContactDetails.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ContactDetails().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    fun setupClickListeners(context: Context, listOfViews: List<Pair<View, String>>) {
        for ((view, message) in listOfViews) {
            view.setOnClickListener {
                if(message == "Going back"){
                    parentFragmentManager.popBackStack()
                }
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }
            view.isClickable = true
            view.isFocusable = true
        }
    }
}