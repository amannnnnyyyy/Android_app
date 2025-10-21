package com.example.myapplication1.weather.views

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.myapplication1.R
import com.example.myapplication1.databinding.FragmentWeatherMainBinding
import com.example.myapplication1.weather.api.WeatherServiceApi
import com.example.myapplication1.weather.models.WeatherResponse
import com.example.myapplication1.weather.repository.WeatherRepository
import com.example.myapplication1.weather.utils.WeatherConstants.BASE_URL
import com.example.myapplication1.weather.utils.WeatherConstants.METRIC_UNIT
import com.example.myapplication1.weather.utils.WeatherConstants.WEATHER_API_KEY
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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


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
                        is WeatherResource.Loading -> Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT)
                            .show()

                        is WeatherResource.Success -> Toast.makeText(requireContext(), "Successful ${res.data}", Toast.LENGTH_SHORT)
                            .show()

                        is WeatherResource.Error -> {
                            Log.e("WeatherData", "onCreateView: ${res.message}", )
                            Toast.makeText(
                                requireContext(),
                                "Error : ${res.message}",
                                Toast.LENGTH_SHORT
                            )
                                .show()
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


    override fun onPause() {
        super.onPause()
        mFusedLocationClient.removeLocationUpdates(locationCallback)
    }



}