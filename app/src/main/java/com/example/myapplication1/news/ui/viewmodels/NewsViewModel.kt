package com.example.myapplication1.news.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication1.news.models.NewsResponse
import com.example.myapplication1.news.repository.NewsRepository
import com.example.myapplication1.news.utils.Resource
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(
    val repository: NewsRepository
): ViewModel() {

    private val _breakingNewsFlow: MutableStateFlow<Resource<NewsResponse>> = MutableStateFlow(Resource.Loading())
    val breakingNewsFlow = _breakingNewsFlow.asStateFlow()

    private val _searchedNewsFlow: MutableStateFlow<Resource<NewsResponse>> = MutableStateFlow(Resource.Loading())
    val searchedNewsFlow = _searchedNewsFlow.asStateFlow()

    var breakingNewsPage = 1
    var searchNewsPage = 1
    private val _breakingNewsCountry = MutableSharedFlow<String>()
    val breakingNewsCountry = _breakingNewsCountry.asSharedFlow()
    private val _searchQuery = MutableStateFlow<String>("")



    init {
        viewModelScope.launch {
            _breakingNewsCountry.emit("us")
        }

        viewModelScope.launch {
            _breakingNewsCountry.collectLatest { countryCode ->
                getBreakingNews(countryCode)
            }
        }
    }


    fun changeCountry(country: String){
        viewModelScope.launch {
            _breakingNewsCountry.emit(country)
        }
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



    private fun handleSearchNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse>{
        if (response.isSuccessful){
            response.body()?.let{ result->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }


    fun searchNews(searchQuery:String){
        viewModelScope.launch {
            _searchQuery.collectLatest { query->
                _searchedNewsFlow.value = Resource.Loading()
                val searchResponse = repository.searchNews(searchQuery, searchNewsPage)
                _searchedNewsFlow.value = handleSearchNewsResponse(searchResponse)
            }
        }
    }

    fun changeSearchQuery(searchQuery:String){
        _searchQuery.value = searchQuery
    }

    fun changePage(page:Int){
        breakingNewsPage = page
    }
}