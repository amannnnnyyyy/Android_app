package com.example.myapplication1.view.fragments.home

import android.icu.text.RelativeDateTimeFormatter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable
import androidx.compose.ui.graphics.Path
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.findNavController
import com.example.myapplication1.R
import com.example.myapplication1.databinding.FragmentHomeMainBinding
import com.google.android.material.navigation.NavigationView
import androidx.core.view.get
import androidx.core.view.iterator

class MainHomeFragment : Fragment(R.layout.fragment_home_main),
    NavigationView.OnNavigationItemSelectedListener {

        private lateinit var drawerLayout: DrawerLayout
        private lateinit var navigationView: NavigationView



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentHomeMainBinding.inflate(inflater, container, false)

        drawerLayout = binding.drawerLayout
        navigationView = binding.navView
        val toolbar = binding.toolbar2

        navigationView.bringToFront()

//        (activity as? AppCompatActivity)?.setSupportActionBar(toolbar)

        //val toggle = ActionBarDrawerToggle(requireActivity(), drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer)

        binding.toggle.setOnClickListener {
            drawerLayout.open()
        }

//        drawerLayout.addDrawerListener(toggle)
//        toggle.syncState()


        binding.chat.setOnClickListener {
            navigateToChat()
        }

        binding.workout.setOnClickListener {
            navigateToWorkOut()
        }

        binding.news.setOnClickListener {
            navigateToNews()
        }

        navigationView.setNavigationItemSelectedListener(this)

        return binding.root
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        when(menuItem.itemId){
            R.id.chat -> navigateToChat()
            R.id.workout -> navigateToWorkOut()
            R.id.news -> navigateToNews()
        }

        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }



    fun navigateToChat(){
//        for (menu in navigationView.menu){
//            menu.isCheckable = false
//        }
        val nav = findNavController()
        val direction = MainHomeFragmentDirections.actionMainHomeFragmentToChatHolderFragment()
        nav.navigate(direction)
    }

    fun navigateToWorkOut(){
        val nav = findNavController()
        val direction = MainHomeFragmentDirections.actionMainHomeFragmentToWorkOutHomeFragment()
        nav.navigate(direction)
    }

    fun navigateToNews(){
        val nav = findNavController()
        val direction = MainHomeFragmentDirections.actionMainHomeFragmentToNewsMainFragment()
        nav.navigate(direction)
    }

    companion object {

        fun newInstance(param1: String, param2: String) =
            MainHomeFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}