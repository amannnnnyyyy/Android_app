package com.example.myapplication1

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.core.view.get
import com.example.myapplication1.databinding.FragmentMainChatsBinding

@RequiresApi(Build.VERSION_CODES.O)

class MainChats : Fragment(R.layout.fragment_main_chats){

    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding: FragmentMainChatsBinding = FragmentMainChatsBinding.inflate(inflater, container, false)

            bottomNavigationView = binding.footer
            val frag_holder = binding.chatsFragHolder
            handlePageSwitch(frag_holder)


    return binding.root
    }




    private fun handlePageSwitch(frag_holder: ViewPager2){

        frag_holder.let{
            handleBottomNavigationView(it)
            handleViewPager2(it)
        }

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.chatsWithRecycler -> frag_holder.currentItem = 0
                R.id.contactDetails2 -> frag_holder.currentItem = 1
            }
            true
        }
    }


    fun handleBottomNavigationView(frag_holder: ViewPager2){
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.chatsWithRecycler -> frag_holder.currentItem = 0
                R.id.contactDetails2 -> frag_holder.currentItem = 1
            }
            true
        }
    }


    fun handleViewPager2(frag_holder: ViewPager2){
        val fragmentList = arrayListOf<Fragment>(
            Chats_display_fragment(),
            ContactDetails()
        )
        val adapter = ViewPagerAdapter(fragmentList, requireActivity().supportFragmentManager, lifecycle)

        frag_holder.adapter = adapter


        frag_holder.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                bottomNavigationView.menu[position].isChecked = true

            }
        })

    }

}