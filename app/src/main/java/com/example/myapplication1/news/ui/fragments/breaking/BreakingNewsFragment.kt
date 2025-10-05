package com.example.myapplication1.news.ui.fragments.breaking

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication1.R
import com.example.myapplication1.databinding.FragmentBreakingNewsBinding
import com.example.myapplication1.news.adapters.NewsAdapter
import com.example.myapplication1.news.db.ArticleDatabase
import com.example.myapplication1.news.repository.NewsRepository
import com.example.myapplication1.news.ui.viewmodels.NewsViewModel
import com.example.myapplication1.news.ui.viewmodels.NewsViewModelProviderFactory
import com.example.myapplication1.news.utils.Resource

class BreakingNewsFragment : Fragment(R.layout.fragment_breaking_news,) {
    private val viewModel: NewsViewModel by lazy {
        val database = ArticleDatabase.getDatabase(requireContext())
        val repository = NewsRepository(database)
        val factory = NewsViewModelProviderFactory(repository)
        ViewModelProvider(this, factory)[NewsViewModel::class.java]
    }
    private lateinit var newsAdapter : NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentBreakingNewsBinding.inflate(inflater, container, false)

        setUpRecycler(binding)

        viewModel.breakingNews.observe(viewLifecycleOwner){ response->
            when(response){
                is Resource.Success-> {
                    hideProgressBar()
                    response.data?.let{ news->
                        newsAdapter.differ.submitList(news.articles)
                    }
                }
                is Resource.Loading -> showProgressBar()
                is Resource.Error-> {
                    hideProgressBar()
                    response.message?.let{errorMessage->
                        Toast.makeText(requireContext(), "Error: $errorMessage", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        return binding.root
    }


    private fun hideProgressBar(){

    }


    private fun showProgressBar(){

    }


    fun setUpRecycler(binding: FragmentBreakingNewsBinding){
        newsAdapter = NewsAdapter()
        binding.recyclerBreakingNews.apply{
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BreakingNewsFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}