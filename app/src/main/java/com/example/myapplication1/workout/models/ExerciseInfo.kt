package com.example.myapplication1.workout.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "exercise_info"
)
data class ExerciseInfo(
    val author_history: List<String>?,
    val category: Category?,
    val created: String?,
    val equipment: List<Equipment>?,
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val images: List<Images>?,
    val last_update: String?,
    val last_update_global: String?,
    val license: License?,
    val license_author: String?,
    val muscles: List<Muscle>?,
    val muscles_secondary: List<MusclesSecondary>?,
    val total_authors_history: List<String>?,
    val translations: List<Translation>?,
    val uuid: String?,
    val variations: Int?,
    val videos: List<Video>?
)