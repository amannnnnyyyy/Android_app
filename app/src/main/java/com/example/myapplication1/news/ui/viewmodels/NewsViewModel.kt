package com.example.myapplication1.news.ui.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication1.news.models.NewsResponse
import com.example.myapplication1.news.repository.NewsRepository
import com.example.myapplication1.news.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(
    val repository: NewsRepository
): ViewModel() {

    private val _breakingNewsFlow: MutableStateFlow<Resource<NewsResponse>> = MutableStateFlow(Resource.Loading())
    val breakingNewsFlow = _breakingNewsFlow.asStateFlow()
    var breakingNewsPage = 1


    init {
        getBreakingNews("us")
    }

    fun getBreakingNews(countryCode:String) = viewModelScope.launch {
        _breakingNewsFlow.value = Resource.Loading()
        val breakingNewsResponse = repository.getBreakingNews(countryCode, breakingNewsPage)
        _breakingNewsFlow.value = handleBreakingNewsResponse(breakingNewsResponse)
    }


    private fun handleBreakingNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse>{
        if (response.isSuccessful){
            response.body()?.let{ result->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }
}