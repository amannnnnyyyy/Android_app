package com.example.myapplication1.workout.models

import androidx.compose.runtime.snapshots.SnapshotId

data class WorkoutPlan(
    val id: Int,
    val date: DaysOfWeek,
    val workoutCategory:String

)