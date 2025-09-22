package com.example.myapplication1

import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.time.LocalDate
import androidx.core.view.get
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

@RequiresApi(Build.VERSION_CODES.O)

class MainChats : Fragment(){

    //private lateinit var viewPager: ViewPager2
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
       // viewPager = view.findViewById(R.id.chats_frag_holder)
//        bottomNavigationView = view.findViewById<BottomNavigationView>(R.id.footer)
//        val frag_holder = view.findViewById<FragmentContainerView>(R.id.chats_frag_holder)
//
//       //val navControllerMain = Navigation.findNavController(frag_holder)
//        val navController = frag_holder.findNavController()
//       bottomNavigationView.setupWithNavController(navController)

       // viewPager.

      //  adapter = ViewPagerAdapter(parentFragmentManager,lifecycle)
        //viewPager.adapter = adapter

//        bottomNavigationView.setOnNavigationItemSelectedListener{ menu->
//            val fragment = when(menu.itemId) {
//                R.id.chatsWithRecycler -> Chats_display_fragment()
//                R.id.contactsList2 -> ContactsList()
//                else -> null
//            }
//
//            fragment?.let {
//                childFragmentManager.beginTransaction()
//                    .replace(R.id.chats_frag_holder, it)
//                    .commit()
//            }
//            true
//        }

//        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
//            override fun onPageSelected(position: Int) {
//                super.onPageSelected(position)
//                val menuItem = bottomNavigationView.menu[position]
//                bottomNavigationView.selectedItemId = menuItem.itemId
//                //bottomNavigationView.menu[position].isChecked = true
//            }
//        })


    return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            //val fragHolder = view.findViewById<FragmentContainerView>(R.id.chats_frag_holder)
            //childFragmentManager.beginTransaction().replace(fragHolder.id, Chats_display_fragment()).commit()
            bottomNavigationView = view.findViewById<BottomNavigationView>(R.id.footer)
            val frag_holder = view.findViewById<FragmentContainerView>(R.id.chats_frag_holder)

            val navHostFragment = childFragmentManager.findFragmentById(R.id.chats_frag_holder) as? NavHostFragment
            //val navControllerMain = Navigation.findNavController(frag_holder)
            if (navHostFragment != null) {
                val navController = navHostFragment.navController
                println("NavController graph ID: ${navController.graph.id}")
                bottomNavigationView.setupWithNavController(navController)

                navController.addOnDestinationChangedListener { _, destination, _ ->
                    println("Current destination in MainChats: ${destination.id}, Label: ${destination.label}")
                }
            } else {
                println("Error: NavHostFragment not found for chats_frag_holder")
            }


        }
    }


    private fun setFragment(frag: Fragment){
      //  parentFragmentManager.beginTransaction().replace(R.id.chats_frag_holder,frag).commit()
    }

}