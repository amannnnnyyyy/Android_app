package com.example.myapplication1

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.compose.ui.graphics.Color
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ChatAppClone : AppCompatActivity() {
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_chat_app_clone)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        go_back_btn = findViewById<ImageView>(R.id.go_back)
        more_options = findViewById(R.id.more_options)
        user_profile = findViewById(R.id.userProfile)
        profile_name = findViewById(R.id.profile_name)
        user_phone = findViewById(R.id.userPhone)
        last_seen = findViewById(R.id.lastSeen)
        audio_call = findViewById(R.id.audio_call)
        video_call = findViewById(R.id.video_call)
        search = findViewById(R.id.search)
        notification_layout = findViewById(R.id.notification_layout)
        media_layout = findViewById(R.id.media_layout)
        encryption_layout = findViewById(R.id.encryption_layout)
        disappearing_layout = findViewById(R.id.disappear_layout)
        chat_lock = findViewById(R.id.chatLockGroup)
        toggle = findViewById<SwitchCompat>(R.id.toggle)
        chat_privacy_layout = findViewById(R.id.chat_privacy_layout)
        create_group_layout = findViewById(R.id.create_group_layout)
        add_to_favourites_layout = findViewById(R.id.add_to_favourites_layout)
        add_to_list_layout = findViewById(R.id.add_to_list_layout)
        block_layout = findViewById(R.id.block_layout)
        report_layout = findViewById(R.id.report_layout)

        val contact = intent.getSerializableExtra("Person") as Contact
        user_profile.setImageResource(contact.profilePicture)
        profile_name.text = contact.name


}

    override fun onResume() {
        super.onResume()
        setupClickListeners(
            context = this, listOf(
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
    }

    fun setupClickListeners(context: Context, listOfViews: List<Pair<View, String>>) {
        for ((view, message) in listOfViews) {
            view.setOnClickListener {
                if(message == "Going back"){
                    finish()
                }
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }
            view.isClickable = true
            view.isFocusable = true
        }
    }

}