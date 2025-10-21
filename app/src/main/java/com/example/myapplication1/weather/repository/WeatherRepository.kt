package com.example.myapplication1.weather.repository

import com.example.myapplication1.weather.api.RetrofitInstance
import com.example.myapplication1.weather.utils.WeatherConstants.METRIC_UNIT
import com.example.myapplication1.weather.utils.WeatherConstants.WEATHER_API_KEY

class WeatherRepository {
    suspend fun getWeatherData(latitude:Double, longitude:Double) = RetrofitInstance.api.getWeatherDetails(latitude, longitude, WEATHER_API_KEY, METRIC_UNIT)
}