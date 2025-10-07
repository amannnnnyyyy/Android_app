package com.example.myapplication1.news.ui.fragments.breaking

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication1.R
import com.example.myapplication1.databinding.FragmentBreakingNewsBinding
import com.example.myapplication1.news.adapters.NewsAdapter
import com.example.myapplication1.news.db.ArticleDatabase
import com.example.myapplication1.news.models.Article
import com.example.myapplication1.news.models.Source
import com.example.myapplication1.news.repository.NewsRepository
import com.example.myapplication1.news.ui.main.NewsMainFragmentDirections
import com.example.myapplication1.news.ui.viewmodels.NewsViewModel
import com.example.myapplication1.news.ui.viewmodels.NewsViewModelProviderFactory
import com.example.myapplication1.news.utils.NewsConstants.QUERY_PAGE_SIZE
import com.example.myapplication1.news.utils.Resource
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class BreakingNewsFragment : Fragment(R.layout.fragment_breaking_news), AdapterView.OnItemSelectedListener {
    private val viewModel: NewsViewModel by lazy {
        val database = ArticleDatabase.getDatabase(requireContext())
        val repository = NewsRepository(database)
        val factory = NewsViewModelProviderFactory(repository)
        ViewModelProvider(this, factory)[NewsViewModel::class.java]
    }

    lateinit var article:Article
    private lateinit var newsAdapter : NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentBreakingNewsBinding.inflate(inflater, container, false)

        setUpRecycler(binding)

        newsAdapter.setOnItemClickListener {
            Log.i("thisIsIt","title: ${it.title}\ncontent: ${it.content}\npublishedAt: ${it.publishedAt}\nId: ${it.id}\nurlToImage: ${it.urlToImage}\nurl: ${it.url}\ndescription: ${it.description}\nauthor${it.author}\n\nsource: ${it.source}")
            val nav = findNavController()
            val direction = NewsMainFragmentDirections.actionNewsMainFragmentToNewsArticleFragment(it)
            nav.navigate(direction)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.breakingNewsFlow.collectLatest { response->
                    when(response){
                        is Resource.Success-> {
                            hideProgressBar()
                            response.data?.let{ news->
                                newsAdapter.differ.submitList(news.articles.toList())
                                val totalPages = news.totalResults / QUERY_PAGE_SIZE +2
                                isLastPage = viewModel.breakingNewsPage == totalPages

                                if (isLastPage){
                                        binding.rvBreakingNews.setPadding(0,0,0,0)
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


        val numberSpinner: Spinner = binding.countrySpinner

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.countries,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            numberSpinner.adapter = adapter
        }

        numberSpinner.onItemSelectedListener = this


        binding.filterBtn.setOnClickListener {
            numberSpinner.performClick()
        }
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val title =  view.findViewById<TextView>(R.id.title)
        title.text = "What is going on"
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
                viewModel.getNextBreakingNewsPage()
                isScrolling = false
            }
               // }
            //}
        }
    }


    fun setUpRecycler(binding: FragmentBreakingNewsBinding){
        newsAdapter = NewsAdapter()
        binding.rvBreakingNews.apply{
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(this@BreakingNewsFragment.scrollListener)
        }
    }

    override fun onItemSelected(
        parent: AdapterView<*>?,
        view: View?,
        position: Int,
        id: Long
    ) {
        val selectedNumber = parent?.getItemAtPosition(position).toString()
        viewModel.changeCountry(selectedNumber)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BreakingNewsFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}