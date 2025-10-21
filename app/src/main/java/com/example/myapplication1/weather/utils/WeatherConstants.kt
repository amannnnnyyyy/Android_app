package com.example.myapplication1.weather.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

object WeatherConstants {
    const val WEATHER_API_KEY = "04763c266f772ba3b623cac760b5263f"
    const val METRIC_UNIT = "metric"
    const val BASE_URL = "https://api.openweathermap.org/data/"

    fun isNetworkAvailable(context: Context?):Boolean {
        val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager


        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            val network = connectivityManager.activeNetwork?:return false

            val activeNetwork = connectivityManager.getNetworkCapabilities(network)?: return false

            return when{
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)-> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)-> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)-> true
                else-> false
            }

        }else{
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo!= null && networkInfo.isConnectedOrConnecting
        }

        return false
    }
}