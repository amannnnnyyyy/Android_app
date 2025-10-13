package com.example.myapplication1.workout.utils

sealed class Resource<T>(val data:T? = null, val message:String?=null) {
    class Success<T>(data:T): Resource<T>(data){
        override fun toString(): String {
            return this.message?:"Empty data returned"
        }
    }
    class Loading<T>:Resource<T>(){
        override fun toString(): String {
            return "No data returned"
        }
    }
    class Error<T>(data:T?=null, message:String): Resource<T>(data,message){
        override fun toString(): String {
            return this.message?:"Unknown error occurred"
        }
    }
}