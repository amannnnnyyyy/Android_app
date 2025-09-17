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
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.time.LocalDate

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

@RequiresApi(Build.VERSION_CODES.O)

class MainChats : Fragment(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment_main_chats, container, false)

        setFragment(Chats_display_fragment())
        val bottomNavigation = view.findViewById<BottomNavigationView>(R.id.footer)

        bottomNavigation.setOnNavigationItemSelectedListener{
            when(it.itemId){
                R.id.chats -> setFragment(Chats_display_fragment())
                R.id.calls -> setFragment(ContactsList())
            }
            true
        }
    return view
    }

    private fun setFragment(frag: Fragment){
        parentFragmentManager.beginTransaction().replace(R.id.chats_frag_holder,frag).commit()
    }

}