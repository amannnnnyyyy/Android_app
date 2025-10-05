package com.example.myapplication1.news.ui.fragments.searchNews

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication1.R
import com.example.myapplication1.databinding.FragmentBreakingNewsBinding
import com.example.myapplication1.databinding.FragmentSearchNewsBinding
import com.example.myapplication1.news.adapters.NewsAdapter
import com.example.myapplication1.news.db.ArticleDatabase
import com.example.myapplication1.news.repository.NewsRepository
import com.example.myapplication1.news.ui.viewmodels.NewsViewModel
import com.example.myapplication1.news.ui.viewmodels.NewsViewModelProviderFactory
import com.example.myapplication1.news.utils.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.getValue

class SearchNewsFragment : Fragment(R.layout.fragment_search_news) {
    private val viewModel: NewsViewModel by viewModels {
        NewsViewModelProviderFactory(NewsRepository(ArticleDatabase.getDatabase(requireContext())))
    }
    private lateinit var newsAdapter : NewsAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSearchNewsBinding.inflate(inflater, container, false)


        var job: Job? = null
        binding.searchArea.doAfterTextChanged { s->
            job?.cancel()
            job = viewLifecycleOwner.lifecycleScope.launch {
                delay(500)
                viewModel.searchNews(s.toString())
            }
        }


        setUpRecycler(binding)
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.searchedNewsFlow.collectLatest { response->
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
                                Log.d("Error","Error: $errorMessage")
                            }
                        }
                    }
                }
            }
        }

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SearchNewsFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }


    private fun hideProgressBar(){

    }


    private fun showProgressBar(){

    }


    fun setUpRecycler(binding: FragmentSearchNewsBinding){
        newsAdapter = NewsAdapter()
        binding.recyclerSearchNews.apply{
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}