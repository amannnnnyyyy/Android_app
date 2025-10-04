package com.example.myapplication1.news.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.myapplication1.news.repository.NewsRepository

class NewsViewModel(
    val repository: NewsRepository
): ViewModel() {

}