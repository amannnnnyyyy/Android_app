package com.example.myapplication1.news.ui.fragments.searchNews

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.myapplication1.R
import com.example.myapplication1.news.ui.viewmodels.NewsViewModel
import kotlin.getValue

class SearchNewsFragment : Fragment() {
    val viewModel: NewsViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search_news, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SearchNewsFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}