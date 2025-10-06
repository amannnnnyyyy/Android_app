package com.example.myapplication1.news.ui.fragments.breaking

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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