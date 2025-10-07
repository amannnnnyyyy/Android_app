package com.example.myapplication1.workout.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication1.news.models.NewsResponse
import com.example.myapplication1.workout.models.RoutineResponse
import com.example.myapplication1.workout.repository.RoutineRepository
import com.example.myapplication1.workout.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

class WorkOutDetailsViewModel(val repository: RoutineRepository): ViewModel() {
    private val _routines : MutableStateFlow<Resource<RoutineResponse>> = MutableStateFlow(Resource.Loading())
    val routines =  _routines.asStateFlow()

    fun getAllRoutines(){
        viewModelScope.launch {
            val response = repository.getRoutines(10, 0)
            _routines.value = handleRoutineResponse(response)
        }
    }

    private fun handleRoutineResponse(response: Response<RoutineResponse>): Resource<RoutineResponse> {
        if (response.isSuccessful){
            response.body()?.let{ result->
                if (result.routines.isEmpty()){
                    return Resource.Error(null, "No routine data found")
                }else{
                    return Resource.Success(result)
                }
            }
            return Resource.Error(null, "Fetch not successful")
        }
        return Resource.Error(null, response.message())
    }

}