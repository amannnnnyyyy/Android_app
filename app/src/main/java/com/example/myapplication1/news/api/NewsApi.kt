package com.example.myapplication1.news.api

import com.example.myapplication1.news.NewsConstants
import com.example.myapplication1.news.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("/v2/top-headlines")
    suspend fun getBreakingNews(
        @Query("country") countryCode:String = "et",
        @Query("page") pageNumber:Int = 1,
        @Query("apiKey") apiKey:String = NewsConstants.NEWS_API_KEY
    ): List<NewsResponse>


    @GET("/v2/everything")
    suspend fun searchNews(
        @Query("q") searchQuery:String,
        @Query("page") pageNumber:Int = 1,
        @Query("apiKey") apiKey:String = NewsConstants.NEWS_API_KEY
    ): List<NewsResponse>
}