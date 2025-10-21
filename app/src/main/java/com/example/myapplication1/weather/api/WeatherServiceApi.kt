package com.example.myapplication1.weather.api

import com.example.myapplication1.weather.models.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherServiceApi  {
    @GET("2.5/weather")
    suspend fun getWeatherDetails(
        @Query("lat") latitude:Double,
        @Query("lon") longitude:Double,
        @Query("appid") appId: String,
        @Query("units") units: String
    ): Response<WeatherResponse>

}