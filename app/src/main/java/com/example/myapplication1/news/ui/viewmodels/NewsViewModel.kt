package com.example.myapplication1.news.ui.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication1.news.models.NewsResponse
import com.example.myapplication1.news.repository.NewsRepository
import com.example.myapplication1.news.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(
    val repository: NewsRepository
): ViewModel() {

    val breakingNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var breakingNewsPage = 1


    init {
        Log.i("fetching_news","this is initial")
        getBreakingNews("us")
        Log.i("fetching_news","this is after")
    }

    fun getBreakingNews(countryCode:String) = viewModelScope.launch {
        breakingNews.postValue(Resource.Loading())
        val breakingNewsResponse = repository.getBreakingNews(countryCode, breakingNewsPage)
        breakingNews.postValue(handleBreakingNewsResponse(breakingNewsResponse))
        Log.i("fetching_news","this is $breakingNewsResponse")
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