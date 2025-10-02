package com.example.myapplication1

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(
    list:ArrayList<Fragment>, fragmentManager: FragmentManager, lifecycle: Lifecycle)
    : FragmentStateAdapter(fragmentManager,lifecycle) {

       private val fragmentList = list

    @RequiresApi(Build.VERSION_CODES.O)
    override fun createFragment(position: Int): Fragment {
       return fragmentList[position]

    }

    override fun getItemCount(): Int = fragmentList.size

}