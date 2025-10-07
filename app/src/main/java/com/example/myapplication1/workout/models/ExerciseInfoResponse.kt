package com.example.myapplication1.workout.models

data class ExerciseInfoResponse(
    val count:Int,
    val next:String?,
    val previous:String?,
    val results: List<ExerciseInfo>,
)