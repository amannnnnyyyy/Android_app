package com.example.myapplication1.workout.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication1.workout.repository.ExerciseInfoRepository
import com.example.myapplication1.workout.ui.details.WorkOutDetailsViewModel

class ExerciseInfoViewModelProvider(val respository: ExerciseInfoRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return WorkOutDetailsViewModel(respository) as T
    }
}