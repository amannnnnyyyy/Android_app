package com.example.myapplication1.workout.api

import com.example.myapplication1.news.models.NewsResponse
import com.example.myapplication1.news.utils.NewsConstants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ExerciseApi {
    @GET("/api/v2/routine")
    suspend fun getRoutine(
        @Query("limit") limit: Int?,
        @Query("offset") offset:Int?,
        //@Query("apiKey") apiKey:String = NewsConstants.NEWS_API_KEY
    ): Response<NewsResponse>


//    @GET("/v2/everything")
//    suspend fun searchNews(
//        @Query("q") searchQuery:String,
//        @Query("page") pageNumber:Int = 1,
//        @Query("apiKey") apiKey:String = NewsConstants.NEWS_API_KEY
//    ): Response<NewsResponse>
}