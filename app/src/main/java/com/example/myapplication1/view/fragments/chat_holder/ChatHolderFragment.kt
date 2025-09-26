package com.example.myapplication1.view.fragments.chat_holder

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication1.R
import com.example.myapplication1.databinding.FragmentChatHolderBinding
import com.example.myapplication1.view.adapters.view_pager_adapter.ViewPager2Adapter
import com.example.myapplication1.view.fragments.chat_list.ChatListFragment
import com.example.myapplication1.view.fragments.recent_calls.RecentCallsFragment

class ChatHolderFragment : Fragment(R.layout.fragment_chat_holder) {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentChatHolderBinding.inflate(inflater,container,false)

        val bottomNavView = binding.bottomNavView
        val viewPager2 = binding.viewPager

        val fragmentList = arrayListOf<Fragment>(
            ChatListFragment(),
            RecentCallsFragment()
        )


        val viewPagerAdapter = ViewPager2Adapter(this)
        for (fragment in fragmentList) viewPagerAdapter.addFragment(fragment)
        viewPager2.adapter = viewPagerAdapter

        bottomNavView.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.chatsWithRecycler -> viewPager2.currentItem = 0
                R.id.contactDetails2 -> viewPager2.currentItem = 1
            }
            true
        }

        viewPager2.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                bottomNavView.menu.get(position).isChecked = true
            }
        })

        return binding.root
    }
}