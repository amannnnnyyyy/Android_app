package com.example.myapplication1.workout.models

import com.example.myapplication1.workout.ui.workoutplan.Utils.LIST_OF_DATE_INDEXES

data class WorkoutPlan(
    var id: Int?,
    val date: DaysOfWeek,
    val workoutCategory:String
){

    init{
        if (id == null){
            id = LIST_OF_DATE_INDEXES[date]
        }
    }

}