package com.example.myapplication1.workout.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication1.workout.models.ExerciseCategoryResponse
import com.example.myapplication1.workout.repository.ExerciseCategoryRepository
import com.example.myapplication1.workout.utils.Resource
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

class WorkoutHomeViewModel(val repository: ExerciseCategoryRepository): ViewModel() {
    private val _exerciseCategories : MutableStateFlow<Resource<ExerciseCategoryResponse>> = MutableStateFlow(Resource.Loading())
    val exerciseCategory =  _exerciseCategories.asStateFlow()


    init {
        try {
            getAllCategories()
        }catch (e: Error){
            Log.i("checkCategory","${e.message}")
        }
    }

    fun getAllCategories()= viewModelScope.launch {
        val response = async{ repository.getExerciseCategories(null, null) }
        Log.d("thisIsTheAnswer","Inside the viewmodel \n\t ${response}")
        //if (response!=null){
        response.await()?.let { _exerciseCategories.value = handleRoutineResponse(it) }
       // }
    }

    private fun handleRoutineResponse(response: Response<ExerciseCategoryResponse>): Resource<ExerciseCategoryResponse> {
        try {
            Log.i("checkCategory","trying")
            if (response.isSuccessful){
                Log.i("checkCategory","successful")
                response.body()?.let{ result->
                    if (result.results!=null && result.results.isEmpty()){
                        return Resource.Error(null, "No category data found")
                    }else{
                        return Resource.Success(result)
                    }
                }
                return Resource.Error(null, "Fetch not successful")
            }else{
                Log.i("checkCategory","failed $response")
                return Resource.Error(null, "Categories fetch not successful")
            }
        }catch (e: Error){
            return Resource.Error(null, if((e.message?.length?:0)>1)e.message.toString() else "Unknown error occurred")
        }
        return Resource.Error(null, if (response.message().length>1)response.message() else "Unknown error occurred")
    }
}