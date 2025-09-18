package com.example.myapplication1

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle)
    : FragmentStateAdapter(fragmentManager,lifecycle) {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun createFragment(position: Int): Fragment {
       return when(position){
           0-> Chats_display_fragment()
           3-> ContactsList()
          // 2-> Chats_display_fragment()
          // 3-> ContactsList()
           else-> Chats_display_fragment()
       }

    }

    override fun getItemCount(): Int = 4

}