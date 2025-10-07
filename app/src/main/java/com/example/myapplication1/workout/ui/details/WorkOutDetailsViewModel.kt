package com.example.myapplication1.workout.ui.details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication1.news.models.NewsResponse
import com.example.myapplication1.workout.models.ExerciseCategoryResponse
import com.example.myapplication1.workout.repository.ExerciseCategoryRepository
import com.example.myapplication1.workout.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

class WorkOutDetailsViewModel(val repository: ExerciseCategoryRepository): ViewModel() {
    private val _exerciseCategories : MutableStateFlow<Resource<ExerciseCategoryResponse>> = MutableStateFlow(Resource.Loading())
    val exerciseCategory =  _exerciseCategories.asStateFlow()


    init {
        getAllRoutines()
    }

    fun getAllRoutines()= viewModelScope.launch {
            val response = repository.getExerciseCategories(null, null)
            Log.d("thisIsTheAnswer","Inside the viewmodel \n\t ${response.body()}")
            _exerciseCategories.value = handleRoutineResponse(response)
        }

    private fun handleRoutineResponse(response: Response<ExerciseCategoryResponse>): Resource<ExerciseCategoryResponse> {
        if (response.isSuccessful){
            response.body()?.let{ result->
                if (result.results!=null && result.results.isEmpty()){
                    return Resource.Error(null, "No category data found")
                }else{
                    return Resource.Success(result)
                }
            }
            return Resource.Error(null, "Fetch not successful")
        }
        return Resource.Error(null, response.message())
    }

}