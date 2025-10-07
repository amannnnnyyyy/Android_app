package com.example.myapplication1.workout.models

data class MusclesSecondary(
    val id: Int,
    val image_url_main: String,
    val image_url_secondary: String,
    val is_front: Boolean,
    val name: String,
    val name_en: String
)