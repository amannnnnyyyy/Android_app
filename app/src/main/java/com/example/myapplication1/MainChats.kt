package com.example.myapplication1

import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.time.LocalDate
import androidx.core.view.get

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

@RequiresApi(Build.VERSION_CODES.O)

class MainChats : Fragment(){

    private lateinit var viewPager: ViewPager2
    private lateinit var bottomNavigationView: BottomNavigationView

    private lateinit var adapter: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment_main_chats, container, false)

        //setFragment(Chats_display_fragment())
        viewPager = view.findViewById(R.id.chats_frag_holder)
        bottomNavigationView = view.findViewById<BottomNavigationView>(R.id.footer)

        adapter = ViewPagerAdapter(parentFragmentManager,lifecycle)
        viewPager.adapter = adapter

        bottomNavigationView.setOnNavigationItemSelectedListener{ menu->
            when(menu.itemId){
                R.id.chats -> viewPager.currentItem = 0
                //R.id.updates -> viewPager.currentItem = 1
               // R.id.communities -> viewPager.currentItem = 2
                R.id.calls -> viewPager.currentItem = 3
            }
            true
        }

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                val menuItem = bottomNavigationView.menu[position]
                bottomNavigationView.selectedItemId = menuItem.itemId
                //bottomNavigationView.menu[position].isChecked = true
            }
        })


    return view
    }


    private fun setFragment(frag: Fragment){
        parentFragmentManager.beginTransaction().replace(R.id.chats_frag_holder,frag).commit()
    }

}