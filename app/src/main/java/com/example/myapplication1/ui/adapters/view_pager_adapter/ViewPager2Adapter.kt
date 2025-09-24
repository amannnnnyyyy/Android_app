package com.example.myapplication1.ui.adapters.view_pager_adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPager2Adapter(fragment: Fragment)
    :FragmentStateAdapter(fragment){

        val fragmentList = ArrayList<Fragment>()

    fun addFragment(fragment: Fragment){
        fragmentList.add(fragment)
    }

    override fun createFragment(position: Int): Fragment =fragmentList[position]

    override fun getItemCount(): Int = fragmentList.size
}