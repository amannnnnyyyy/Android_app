package com.example.myapplication1.workout.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication1.workout.models.ExerciseInfoResponse
import com.example.myapplication1.workout.repository.ExerciseInfoRepository
import com.example.myapplication1.workout.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

class WorkOutDetailsViewModel(val repository: ExerciseInfoRepository): ViewModel() {
    private val _exerciseInfos : MutableStateFlow<Resource<ExerciseInfoResponse>> = MutableStateFlow(Resource.Loading())
    val exerciseInfos =  _exerciseInfos.asStateFlow()



    fun getAllInfos(category:Int)= viewModelScope.launch {
        val response = repository.getExerciseInfos(
            category, 10, offset = 0, variations = null
        )
        _exerciseInfos.value = handleRoutineResponse(response)
    }

    private fun handleRoutineResponse(response: Response<ExerciseInfoResponse>): Resource<ExerciseInfoResponse> {
        if (response.isSuccessful){
            response.body()?.let{ result->
                if (result.results!=null && result.results.isEmpty()){
                    return Resource.Error(null, "No detail data found")
                }else{
                    return Resource.Success(result)
                }
            }
            return Resource.Error(null, "Fetch not successful")
        }
        return Resource.Error(null, response.message())
    }
}