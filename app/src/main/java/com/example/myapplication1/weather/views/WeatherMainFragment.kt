package com.example.myapplication1.weather.views

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.myapplication1.R
import com.example.myapplication1.databinding.FragmentWeatherMainBinding
import com.example.myapplication1.weather.models.WeatherTypes
import com.example.myapplication1.weather.repository.WeatherRepository
import com.example.myapplication1.weather.utils.WeatherConstants.isNetworkAvailable
import com.example.myapplication1.weather.utils.WeatherResource
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.datetime.Instant
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.TimeZone


class WeatherMainFragment : Fragment(R.layout.fragment_weather_main) {

    lateinit var viewModel: WeatherMainViewmodel

    private lateinit var mFusedLocationClient: FusedLocationProviderClient

    private val requestPermissionLauncher: ActivityResultLauncher<String> = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            Toast.makeText(requireContext(), "Permission granted", Toast.LENGTH_SHORT).show()
            requestLocationData()
        } else {
            showPermissionDeniedDialog()
            Toast.makeText(requireContext(), "Permission not granted", Toast.LENGTH_SHORT).show()
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentWeatherMainBinding.inflate(inflater, container, false)

        val weatherRepository = WeatherRepository()
        val viewModelProviderFactory = WeatherViewModelProvider(weatherRepository)

        viewModel = ViewModelProvider(this,viewModelProviderFactory).get(WeatherMainViewmodel::class)


        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.weatherData.collectLatest { res ->
                    when(res){
                        is WeatherResource.Loading -> {
                            binding.errorViewer.isVisible = false
                            binding.loading.isVisible = true
                        }

                        is WeatherResource.Success -> {
                            binding.loading.isVisible = false
                            binding.errorViewer.isVisible = false

                            val areaView = binding.cityName
                            val dateView = binding.date
                            val weatherTypeView = binding.weatherType
                            val tempDegreeView = binding.degreeTemp
                            val minTempView = binding.tempDetailsMin
                            val maxTempView = binding.tempDetailsMax
                            val feelsLikeView = binding.feelsLike
                            val sunRiseView = binding.sunRiseValue
                            val sunsetView = binding.sunSetValue
                            val windView = binding.windValue
                            val pressureView = binding.pressureValue
                            val humidityView = binding.humidityValue
                            val moreInfoView = binding.moreInfo

                            val timeZone = TimeZone.currentSystemDefault()

                            res.data?.let { data->

                                val weatherType = data.weather?.get(0)?.main
                                when(weatherType){
                                    WeatherTypes.Clear -> binding.backgroundImage.setImageResource(R.drawable.clear_sky)
                                    WeatherTypes.Rain -> binding.backgroundImage.setImageResource(R.drawable.weather_bkg)
                                    WeatherTypes.Clouds -> binding.backgroundImage.setImageResource(R.drawable.cloud_bkg)
                                    WeatherTypes.Snow -> binding.backgroundImage.setImageResource(R.drawable.snow_bkg)
                                    WeatherTypes.Fog -> binding.backgroundImage.setImageResource(R.drawable.foggy_bkg)
                                    WeatherTypes.Haze -> binding.backgroundImage.setImageResource(R.drawable.haze_bkg)
                                    WeatherTypes.Mist -> binding.backgroundImage.setImageResource(R.drawable.mist_bkg)
                                    WeatherTypes.Drizzle -> binding.backgroundImage.setImageResource(R.drawable.drizzle_bkg)
                                    WeatherTypes.Thunderstorm -> binding.backgroundImage.setImageResource(R.drawable.thunderstorm_bkg)
                                    else->null
                                }


                                val areaData = data.name
                                val dateData = data.dt
                                val weatherTypeData = data.weather?.getOrNull(0)?.description
                                val tempDegreeData = data.main?.temp
                                val minTempData = data.main?.temp_min
                                val maxTempData = data.main?.temp_max
                                val feelsLikeData = data.main?.feels_like
                                val sunRiseData = data.sys?.sunrise
                                val sunsetData = data.sys?.sunset
                                val windDegree:String? = data.wind?.deg?.toString()
                                val windGust: String? = data.wind?.gust?.toString()
                                val windSpeed: String? = data.wind?.speed?.toString()

                                val windData = (windDegree?.let { it+"°" }?:"") +" "+ (windGust?.let { "gs "+it }?:"") +" "+ (windSpeed?.let { "sp "+it }?:"")
                                val pressureData = data.main?.pressure
                                val humidityData = data.main?.humidity


                                areaData?.let { areaView.text = it }
                                dateData?.let { dateView.text = convertUnixTime(it, "full") }
                                weatherTypeData?.let { weatherTypeView.text = it }
                                tempDegreeData?.let { tempDegreeView.text = FahrenheitToCelcius(it) }
                                minTempData?.let { minTempView.text = "Min: "+FahrenheitToCelcius(it) }
                                maxTempData?.let { maxTempView.text = "Max: "+FahrenheitToCelcius(it) }
                                feelsLikeData?.let { feelsLikeView.text = "Feels like "+FahrenheitToCelcius(it) }
                                sunRiseData?.let { sunRiseView.text = convertUnixTime(it) }
                                sunsetData?.let { sunsetView.text = convertUnixTime(it) }
                                windData.let { windView.text = it }
                                pressureData?.let { pressureView.text = it.toString()+" hPa" }
                                humidityData?.let { humidityView.text = it.toString()+"%" }
                            }

                        }

                        is WeatherResource.Error -> {
                            binding.loading.isVisible = false
                            binding.errorViewer.isVisible = true

                            binding.errorMessage.text = res.message
                        }
                    }
                }
            }
        }


        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        return binding.root
    }


    override fun onResume() {
        super.onResume()
        startLocationFlow()
    }

    private fun startLocationFlow() {
        if (!isLocationEnabled()) {
            Toast.makeText(requireContext(), "Location services are disabled. Please enable them.", Toast.LENGTH_LONG).show()
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivity(intent)
        } else {
            checkLocationPermission()
        }
    }


    private fun getLocationWeatherDetails(latitude:Double?, longitude:Double?){
        if (latitude==null || longitude==null){
            return
        }

        if (isNetworkAvailable(requireContext())){
            viewModel.getWeatherData(latitude, longitude)
        }else{
            Toast.makeText(requireContext(), "No internet connection", Toast.LENGTH_SHORT).show()
        }
    }



    private fun checkLocationPermission() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                requestLocationData()
            }
            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                showPermissionRationaleDialog()
            }
            else -> {
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }



    private fun isLocationEnabled(): Boolean {
        val locationManager = requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    private fun requestLocationData() {
        try {
            val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 10000)
                .setWaitForAccurateLocation(false)
                .setMinUpdateIntervalMillis(5000)
                .setMaxUpdateDelayMillis(10000)
                .build()

            mFusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.myLooper()
            )
        } catch (e: SecurityException) {
        }
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val lastLocation = locationResult.lastLocation
            if (lastLocation != null) {
                getLocationWeatherDetails(locationResult.lastLocation?.latitude, locationResult.lastLocation?.longitude)
                mFusedLocationClient.removeLocationUpdates(this)
            }
        }
    }

    private fun showPermissionRationaleDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Location Permission Needed")
            .setMessage("This app needs the Location permission to provide weather data for your current location.")
            .setPositiveButton("OK") { _, _ ->
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
            .setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
            .create()
            .show()
    }

    private fun showPermissionDeniedDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Permission Denied")
            .setMessage("Without location permission, the weather feature is unavailable. You can grant the permission in the app settings.")
            .setPositiveButton("Go to Settings") { _, _ ->
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri = Uri.fromParts("package", requireActivity().packageName, null)
                intent.data = uri
                startActivity(intent)
            }
            .setNegativeButton("Close") { dialog, _ -> dialog.cancel() }
            .show()
    }


    private fun FahrenheitToCelcius(tempF:Double):String = tempF.toString()+"°C"

    @SuppressLint("DefaultLocale")
    private fun convertUnixTime(unixTime:Int, full:String?=null):String{
        val timeZone = TimeZone.currentSystemDefault()
        val localDateTime = Instant.fromEpochSeconds(unixTime.toLong()).toLocalDateTime(timeZone)



        val hour = localDateTime.hour
        val minute = localDateTime.minute

        val amPm = if (hour < 12) "AM" else "PM"
        val hour12 = when {
            hour == 0 -> 12
            hour > 12 -> hour - 12
            else -> hour
        }
        if (full!=null){
            return String.format(
                "%04d-%02d-%02d %02d:%02d %s",
                localDateTime.year,
                localDateTime.monthNumber,
                localDateTime.dayOfMonth,
                localDateTime.hour,
                localDateTime.minute,
                amPm
            )
        }

        val formattedTime = "%d:%02d %s".format(hour12, minute, amPm)

        return formattedTime.toString()
    }


    override fun onPause() {
        super.onPause()
        mFusedLocationClient.removeLocationUpdates(locationCallback)
    }



}