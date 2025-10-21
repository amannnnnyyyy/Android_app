package com.example.myapplication1.weather.utils

sealed class WeatherResource<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T, message: String?): WeatherResource<T>(data, message)
    class Loading<T>(): WeatherResource<T>()
    class Error<T>(message:String?): WeatherResource<T>(message=message)
}