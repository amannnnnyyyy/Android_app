package com.example.myapplication1.weather.views

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication1.weather.models.WeatherResponse
import com.example.myapplication1.weather.repository.WeatherRepository
import com.example.myapplication1.weather.utils.WeatherResource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

class WeatherMainViewmodel(val weatherRepository: WeatherRepository): ViewModel() {
    private val _weatherData = MutableStateFlow<WeatherResource<WeatherResponse>>(WeatherResource.Loading())
    val weatherData = _weatherData.asStateFlow()

    fun getWeatherData(latitude:Double, longitude:Double){
        viewModelScope.launch {
            val response = weatherRepository.getWeatherData(latitude, longitude)

            handleWeatherDataResponse(response)
        }
    }


    fun handleWeatherDataResponse(response: Response<WeatherResponse>){
        if (response.isSuccessful && response.body()!=null){
            val weatherRes = response.body()
            weatherRes?.let { _weatherData.value = WeatherResource.Success(it, "Successful") }
        }else if (response.errorBody()!=null){
            _weatherData.value = WeatherResource.Error(response.errorBody().toString() + response.message())
        }
        else{
            _weatherData.value = WeatherResource.Error("No data")
        }
    }
}