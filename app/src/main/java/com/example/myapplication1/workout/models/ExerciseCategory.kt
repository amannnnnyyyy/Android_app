package com.example.myapplication1.workout.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "categories"
)
data class ExerciseCategory(
    @PrimaryKey(autoGenerate = true)
    val id:Int?=null,
    val name: String?,
//    val next: String?,
//    val previous: String?,
//    val results: List<ResultX>?
)