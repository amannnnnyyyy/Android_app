package com.example.myapplication1.weather.views

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication1.weather.repository.WeatherRepository

class WeatherViewModelProvider(val weatherRepository: WeatherRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return WeatherMainViewmodel(weatherRepository) as T
    }
}