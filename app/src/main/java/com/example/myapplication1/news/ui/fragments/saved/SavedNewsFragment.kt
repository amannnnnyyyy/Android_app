package com.example.myapplication1.news.ui.fragments.saved

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication1.R
import com.example.myapplication1.databinding.FragmentSavedNewsBinding
import com.example.myapplication1.news.adapters.NewsAdapter
import com.example.myapplication1.news.db.ArticleDatabase
import com.example.myapplication1.news.repository.NewsRepository
import com.example.myapplication1.news.ui.main.NewsMainFragmentDirections
import com.example.myapplication1.news.ui.viewmodels.NewsViewModel
import com.example.myapplication1.news.ui.viewmodels.NewsViewModelProviderFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.getValue

class SavedNewsFragment : Fragment(R.layout.fragment_saved_news) {
    private val viewModel: NewsViewModel by viewModels {
        NewsViewModelProviderFactory(NewsRepository(ArticleDatabase.getDatabase(requireContext())))
    }

    lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSavedNewsBinding.inflate(inflater, container, false)

        viewModel.getAllArticles()



        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.savedArticles.observe(viewLifecycleOwner) { articles->
                    newsAdapter = NewsAdapter()
                    newsAdapter.differ.submitList(articles)
                    newsAdapter.setOnItemClickListener {
                        val nav = findNavController()
                        val direction = NewsMainFragmentDirections.actionNewsMainFragmentToNewsArticleFragment(it)
                        nav.navigate(direction)
                    }



                    newsAdapter.setOnLongClick { article ->
                        val builder = AlertDialog.Builder(requireContext()).apply {
                            setTitle("Do you want to delete this article?")
                                .setPositiveButton("Yes"){ dialog, id->
                                    viewModel.deleteArticle(article)
                                    Snackbar.make(binding.root, "Article Deleted Successfully!", Snackbar.LENGTH_SHORT).apply {
                                        setAction("Undo"){
                                            viewModel.saveArticle(article)
                                        }
                                        show()
                                    }
                                }
                                .setNegativeButton("No"){dialog, id->

                                }
                        }
                        builder.create().show()
                    }

                    binding.recyclerSavedNews.apply{
                        adapter = newsAdapter
                        layoutManager = LinearLayoutManager(activity)
                    }
                }

            }
        }




        val swipeDelete = object: ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(
                viewHolder: RecyclerView.ViewHolder,
                direction: Int
            ) {
                val position = viewHolder.adapterPosition
                val article = newsAdapter.differ.currentList[position]
                viewModel.deleteArticle(article)
                Snackbar.make(binding.root, "Article Deleted Successfully!", Snackbar.LENGTH_SHORT).apply {
                    setAction("Undo"){
                        viewModel.saveArticle(article)
                    }
                    show()
                }
            }

        }

        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SavedNewsFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}