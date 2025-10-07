package com.example.myapplication1.workout.models

data class Video(
    val author_history: List<String>,
    val codec: String,
    val codec_long: String,
    val duration: String,
    val exercise: Int,
    val height: Int,
    val id: Int,
    val is_main: Boolean,
    val license: Int,
    val license_author: String,
    val license_author_url: String,
    val license_derivative_source_url: String,
    val license_object_url: String,
    val license_title: String,
    val size: Int,
    val uuid: String,
    val video: String,
    val width: Int
)