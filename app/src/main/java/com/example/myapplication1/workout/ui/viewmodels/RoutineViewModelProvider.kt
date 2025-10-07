package com.example.myapplication1.workout.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication1.workout.repository.RoutineRepository
import com.example.myapplication1.workout.ui.details.WorkOutDetailsViewModel

class RoutineViewModelProvider(val respository: RoutineRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return WorkOutDetailsViewModel(respository) as T
    }
}