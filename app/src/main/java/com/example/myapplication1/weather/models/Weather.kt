package com.example.myapplication1.weather.models

data class Weather(
    val description: String?,
    val icon: String?,
    val id: Int?,
    val main: WeatherTypes?
)