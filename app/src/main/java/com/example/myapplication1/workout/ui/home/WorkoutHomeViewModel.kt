package com.example.myapplication1.workout.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class WorkoutHomeViewModel: ViewModel() {
    private val _stateFlow = MutableStateFlow("write something below")
    val stateFlow = _stateFlow.asStateFlow()

    private val _onDefault = MutableStateFlow<Boolean>(true)
    val onDefault = _onDefault.asStateFlow()


    private val _sharedFlow = MutableSharedFlow<Int>(10)
    val sharedFlow = _sharedFlow.asSharedFlow()


    fun setSharedFlow(newNum: Int){
        viewModelScope.launch {
            _sharedFlow.emit(newNum)
        }
    }


    fun changeToDefault(){
        _stateFlow.value = "Default"
    }



    fun triggerFlow(): Flow<Int>{
        return flow {
            repeat(sharedFlow.first()){
                emit(it+1)
                delay(1000L)
            }
        }
    }

    fun changeStateFlow(str:String){
        _stateFlow.value = str
        _onDefault.value = false
    }
}