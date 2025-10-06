package com.example.myapplication1.news.ui.fragments.article

import android.os.Build
import android.os.Bundle
import android.text.format.DateUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.myapplication1.R
import com.example.myapplication1.databinding.FragmentNewsArticleBinding
import com.example.myapplication1.news.db.ArticleDatabase
import com.example.myapplication1.news.repository.NewsRepository
import com.example.myapplication1.news.ui.viewmodels.NewsViewModel
import com.example.myapplication1.news.ui.viewmodels.NewsViewModelProviderFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.Instant
import kotlin.getValue

class NewsArticleFragment : Fragment(R.layout.fragment_news_article) {

    val args : NewsArticleFragmentArgs by navArgs()
    lateinit var binding: FragmentNewsArticleBinding
    private val viewModel: NewsViewModel by viewModels {
        NewsViewModelProviderFactory(NewsRepository(ArticleDatabase.getDatabase(requireContext())))
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsArticleBinding.inflate(inflater, container, false)


        viewLifecycleOwner.lifecycleScope.launch {
            binding.webView.apply {
                webViewClient = WebViewClient()
                args.article.url?.let {
                    loadUrl(it)
                }
            }
        }

        binding.favourite.setOnClickListener {
            viewModel.saveArticle(args.article)
            Snackbar.make(binding.root, "Article Saved Successfully", Snackbar.LENGTH_SHORT ).show()
        }


        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.favourited.collectLatest { fav->
                    if (fav) binding.favourite.setImageResource(R.drawable.favorite_saved)
                    else binding.favourite.setImageResource(R.drawable.favourite)
                }
            }
        }

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NewsArticleFragment().apply {
            }
    }
}