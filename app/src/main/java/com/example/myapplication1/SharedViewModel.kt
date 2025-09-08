package com.example.myapplication1

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel: ViewModel() {
    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    fun setMessage(msg: String){
        _message.value = msg
    }
}