package com.example.myapplication1.news.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication1.news.models.Article
import com.example.myapplication1.news.models.NewsResponse
import com.example.myapplication1.news.repository.NewsRepository
import com.example.myapplication1.news.utils.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Response
import kotlin.math.ceil

class NewsViewModel(
    val repository: NewsRepository
): ViewModel() {

    private val _breakingNewsFlow: MutableStateFlow<Resource<NewsResponse>> = MutableStateFlow(Resource.Loading())
    val breakingNewsFlow = _breakingNewsFlow.asStateFlow()
    var breakingNewsResponse: NewsResponse? = null
    var isLastPage = false

    var breakingNewsTotalPagesCount = listOf<Int>()
    var totalBreakingNewsPageNumber = 0

    private val _searchedNewsFlow: MutableStateFlow<Resource<NewsResponse>> = MutableStateFlow(Resource.Loading())
    val searchedNewsFlow = _searchedNewsFlow.asStateFlow()
    var searchNewsResponse: NewsResponse? = null

    var breakingNewsPage = 1
    var searchNewsPage = 1
    private var currentCountry = "us"

    private val _searchQuery = MutableStateFlow<String>("")

    val favourited = MutableStateFlow(false)


    private val _savedArticles = MutableLiveData(listOf<Article>())
    var savedArticles: LiveData<List<Article>> = _savedArticles



    init {
        getBreakingNewsForCurrentCountry(shouldReset = true)
        calculatePages()
    }

    fun calculatePages(){
        viewModelScope.launch {
            _breakingNewsFlow.collectLatest { res->
                var addPages = ""
                if((res.data?.articles?.size?:0)>0){
                  val num = ceil((res.data?.totalResults?:1).toDouble() /(20)).toInt()
                    totalBreakingNewsPageNumber = num
                    for(i in 1..num){
                        addPages+=i
                    }
                    Log.i("lookAtPages","$addPages  $num")
                }

                breakingNewsTotalPagesCount = addPages.map { it.toString().toInt() }
            }
        }
    }


    fun getNextPageBreakingNews(page:Int){
        if (page<=breakingNewsTotalPagesCount.last()){
            breakingNewsPage=page
        }
        Log.i("breakingNewsPage","$breakingNewsPage")
        getBreakingNewsForCurrentCountry(false)
    }


    fun changeCountry(country: String){
        currentCountry = country
        getBreakingNewsForCurrentCountry(shouldReset = true)
    }

    fun getNextBreakingNewsPage() {
        Log.i("scrolledToEnd","loading $breakingNewsPage : of : $totalBreakingNewsPageNumber")
        if (breakingNewsPage < totalBreakingNewsPageNumber) breakingNewsPage+=1
        Log.i("scrolledToEnd","made $breakingNewsPage : of : $totalBreakingNewsPageNumber")

        viewModelScope.launch {
            delay(2000)
            getBreakingNewsForCurrentCountry(shouldReset = false)
        }
    }

    private fun getBreakingNewsForCurrentCountry(shouldReset: Boolean) = viewModelScope.launch {
        if (shouldReset) {
            breakingNewsPage = 1
            breakingNewsResponse = null
            isLastPage = false
        }
//        if (isLastPage) {
//            Log.i("breakingNewsPage","last Page detected")
//            return@launch
//        }
        Log.i("breakingNewsPage","fetching")
        _breakingNewsFlow.value = Resource.Loading()
        val response = repository.getBreakingNews(currentCountry, breakingNewsPage)
        _breakingNewsFlow.value = handleBreakingNewsResponse(response)
    }


    private fun handleBreakingNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse>{
        if (response.isSuccessful){
            response.body()?.let{ result->
               // breakingNewsPage++

                if (result.articles.isEmpty()) {
                    isLastPage = true
                }
                //for test only
                if (breakingNewsResponse==null) breakingNewsResponse = result
                else{
                    val oldArticles = breakingNewsResponse?.articles
                    val newArticle = result.articles
                    if (oldArticles?.contains(newArticle.first()) == true) {
                        isLastPage = true
                        return Resource.Success(breakingNewsResponse!!)
                    }
                    oldArticles?.addAll(newArticle)
                }
                return Resource.Success(breakingNewsResponse?:result)
            }
        }
        return Resource.Error(response.message())
    }



    private fun handleSearchNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse>{
        if (response.isSuccessful){
            response.body()?.let{ result->
                searchNewsPage++
                if (searchNewsResponse==null) searchNewsResponse = result
                else{
                    runBlocking {
                        delay(2000)
                    }
                    val oldArticles = searchNewsResponse?.articles
                    val newArticle = result.articles
                    oldArticles?.addAll(newArticle)
                }
                return Resource.Success(searchNewsResponse?:result)
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

    fun saveArticle(article: Article){
        viewModelScope.launch {
            repository.saveArticle(article)
        }
        favourited.value = true
    }

    fun getAllArticles(){
        viewModelScope.launch {
            val result =  repository.getAllArticles()
            savedArticles = result
        }
    }

    fun deleteArticle(article:Article){
        viewModelScope.launch {
            repository.deleteArticle(article)
        }
    }

    fun changeSearchQuery(searchQuery:String){
        _searchQuery.value = searchQuery
    }

    fun changePage(page:Int){
        breakingNewsPage = page
    }
}