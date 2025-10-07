package com.example.myapplication1.workout.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "routines"
)
data class Routine(
    @PrimaryKey(autoGenerate = true)
    val id:Int?=null,
    val count: Int?,
    val next: String?,
    val previous: String?,
    val results: List<Result>?
)