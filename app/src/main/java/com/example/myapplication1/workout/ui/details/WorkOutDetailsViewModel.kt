package com.example.myapplication1.workout.ui.details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication1.workout.models.ExerciseInfo
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

    val selectedExerciseInfo : MutableStateFlow<List<ExerciseInfo>> = MutableStateFlow(listOf<ExerciseInfo>())



    fun setSelectedExercise(exerciseInfos:List<ExerciseInfo>){
        selectedExerciseInfo.value = exerciseInfos
    }
    fun getAllInfos(category:Int)= viewModelScope.launch {
        Log.i("checkCategory","before handle: to fetch")

        val response = repository.getExerciseInfos(
            category, null, offset = null, variations = null
        )
        Log.i("checkCategory","before handle: after fetch $response")

        _exerciseInfos.value = handleRoutineResponse(response)
    }

    private fun handleRoutineResponse(response: Response<ExerciseInfoResponse>): Resource<ExerciseInfoResponse> {
        Log.i("checkCategory","handle: here to handle ${response}")
        if (response.isSuccessful){
            Log.i("checkCategory","handle: is successful ${response}")

            response.body()?.let{ result->
                if (result.results!=null && result.results.isEmpty()){
                    return Resource.Error(null, "No detail data found")
                }else{
                    return Resource.Success(result)
                }
            }
            return Resource.Error(null, "Fetch not successful")
        }
        Log.i("checkCategory","handle: not successful ${response}")

        return Resource.Error(null, response.message())
    }
}