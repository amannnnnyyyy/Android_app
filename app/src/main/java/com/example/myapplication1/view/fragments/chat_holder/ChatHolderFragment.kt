package com.example.myapplication1.view.fragments.chat_holder

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.findNavController
import com.example.myapplication1.R
import com.example.myapplication1.databinding.FragmentChatHolderBinding
import com.example.myapplication1.view.adapters.view_pager_adapter.ViewPager2Adapter
import com.example.myapplication1.view.fragments.chat_list.ChatListFragment
import com.example.myapplication1.view.fragments.home.MainHomeFragmentDirections
import com.example.myapplication1.view.fragments.recent_calls.RecentCallsFragment
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayoutMediator

class ChatHolderFragment : Fragment(R.layout.fragment_chat_holder), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentChatHolderBinding.inflate(inflater,container,false)

        val bottomNavView = binding.tabLayoutPager
        val viewPager2 = binding.viewPager
        drawerLayout = binding.chatDrawerLayout
        toolbar = binding.toolbar2

        binding.navView.bringToFront()

        val toggle = ActionBarDrawerToggle(requireActivity(), drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer)

        drawerLayout.addDrawerListener(toggle)
        binding.navView.menu.removeItem(R.id.chat)
        binding.navView.menu.removeItem(R.id.news)
        binding.navView.menu.removeItem(R.id.workout)
        toggle.syncState()

        binding.navView.setNavigationItemSelectedListener(this)

        val fragmentList = arrayListOf<Fragment>(
            ChatListFragment(),
            RecentCallsFragment()
        )

        val viewPagerAdapter = ViewPager2Adapter(this)
        for (fragment in fragmentList) viewPagerAdapter.addFragment(fragment)
        viewPager2.adapter = viewPagerAdapter

        val options = listOf<String>("Chat","Calls")

        TabLayoutMediator(bottomNavView,viewPager2){tab, pos->
            tab.text = when(pos){
                0->{
                    bottomNavView.getTabAt(0)?.setIcon(R.drawable.chat)
                    options[0]
                }
                1-> {
                    bottomNavView.getTabAt(1)?.setIcon(R.drawable.audio_call)
                    options[1]
                }
                else -> ""
            }
        }.attach()


        val rootView = binding.root
        rootView.viewTreeObserver.addOnGlobalLayoutListener {
            val rect = android.graphics.Rect()
            rootView.getWindowVisibleDisplayFrame(rect)
            val screenHeight = rootView.height
            val keypadHeight = screenHeight - rect.bottom

            if (keypadHeight > screenHeight*0.15) {
                binding.tabLayoutPager.visibility = View.GONE
            }else if(keypadHeight<=screenHeight*0.15){
                binding.tabLayoutPager.visibility = View.VISIBLE
            }
        }

        return binding.root
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        when(menuItem.itemId){
            R.id.home -> findNavController().popBackStack()
        }

        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}