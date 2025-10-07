package com.example.myapplication1.news.ui.fragments.searchNews

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication1.R
import com.example.myapplication1.databinding.FragmentBreakingNewsBinding
import com.example.myapplication1.databinding.FragmentSearchNewsBinding
import com.example.myapplication1.news.adapters.NewsAdapter
import com.example.myapplication1.news.db.ArticleDatabase
import com.example.myapplication1.news.repository.NewsRepository
import com.example.myapplication1.news.ui.main.NewsMainFragmentDirections
import com.example.myapplication1.news.ui.viewmodels.NewsViewModel
import com.example.myapplication1.news.ui.viewmodels.NewsViewModelProviderFactory
import com.example.myapplication1.news.utils.NewsConstants.QUERY_PAGE_SIZE
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

    var searchQuery: String = ""


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
                searchQuery = s.toString()
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
                                val totalPages = news.totalResults / QUERY_PAGE_SIZE +2
                                isLastPage = viewModel.breakingNewsPage == totalPages

                                if (isLastPage){
                                    binding.recyclerSearchNews.setPadding(0,0,0,0)
                                }
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
        isLoading = false
    }


    private fun showProgressBar(){
        isLoading = true
    }

    var isLoading =false
    var isLastPage = false
    var isScrolling = false

    fun setUpRecycler(binding: FragmentSearchNewsBinding){
        newsAdapter = NewsAdapter()
        binding.recyclerSearchNews.apply{
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(this@SearchNewsFragment.scrollListener)
        }

        newsAdapter.setOnItemClickListener {
            val nav = findNavController()
            val direction = NewsMainFragmentDirections.actionNewsMainFragmentToNewsArticleFragment(it)
            nav.navigate(direction)
        }
    }


    val scrollListener = object: RecyclerView.OnScrollListener(){
        override fun onScrollStateChanged(
            recyclerView: RecyclerView,
            newState: Int
        ) {
            super.onScrollStateChanged(recyclerView, newState)

            Log.i("scrolling","set true")
            if (newState== AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                isScrolling = true
            }
        }

        override fun onScrolled(
            recyclerView: RecyclerView,
            dx: Int,
            dy: Int
        ) {
            super.onScrolled(recyclerView, dx, dy)
            Log.i("scrolling","set functionalities")

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isLoading = viewModel.breakingNewsFlow.value is Resource.Loading
            val isLastPage = viewModel.isLastPage // Assumes you've added this to your ViewModel

            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= QUERY_PAGE_SIZE

            val shouldPaginate = !isLoading && !isLastPage && isAtLastItem && isNotAtBeginning &&
                    isTotalMoreThanVisible && isScrolling         //viewLifecycleOwner.lifecycleScope.launch {
            //  viewModel.breakingNewsCountry.collectLatest { country->
            if (shouldPaginate) {
                viewModel.searchNews(searchQuery)
                isScrolling = false
            }
            // }
            //}
        }
    }

}