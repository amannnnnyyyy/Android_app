package com.example.myapplication1.news.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication1.R
import com.example.myapplication1.databinding.FragmentNewsMainBinding
import com.example.myapplication1.news.adapters.NewsViewPagerAdapter
import com.example.myapplication1.news.db.ArticleDatabase
import com.example.myapplication1.news.repository.NewsRepository
import com.example.myapplication1.news.ui.fragments.breaking.BreakingNewsFragment
import com.example.myapplication1.news.ui.fragments.saved.SavedNewsFragment
import com.example.myapplication1.news.ui.fragments.searchNews.SearchNewsFragment
import com.example.myapplication1.news.ui.viewmodels.NewsViewModel
import com.example.myapplication1.news.ui.viewmodels.NewsViewModelProviderFactory


class NewsMainFragment : Fragment(R.layout.fragment_news_main) {

    lateinit var viewModel: NewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val  binding = FragmentNewsMainBinding.inflate(inflater, container, false)

        val newsRepository = NewsRepository(ArticleDatabase.getDatabase(requireContext()))
        val viewModelProviderFactory = NewsViewModelProviderFactory(newsRepository)
        viewModel = ViewModelProvider(this,viewModelProviderFactory).get(NewsViewModel::class)

        val bottomNavView = binding.bottomNavView
        val viewPager2 = binding.newsFragHolder

        val fragmentList = arrayListOf<Fragment>(
            SavedNewsFragment(),
            BreakingNewsFragment(),
            SearchNewsFragment()
        )


        val viewPagerAdapter = NewsViewPagerAdapter(this)
        for (fragment in fragmentList) viewPagerAdapter.addFragment(fragment)
        viewPager2.adapter = viewPagerAdapter

        bottomNavView.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.savedNewsFragment -> viewPager2.currentItem = 0
                R.id.breakingNewsFragment -> viewPager2.currentItem = 1
                R.id.searchNewsFragment -> viewPager2.currentItem = 2
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

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NewsMainFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}