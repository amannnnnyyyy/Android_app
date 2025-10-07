package com.example.myapplication1.workout.models

data class Translation(
    val aliases: List<Any>,
    val author_history: List<String>,
    val created: String,
    val description: String,
    val exercise: Int,
    val id: Int,
    val language: Int,
    val license: Int,
    val license_author: String,
    val license_author_url: String,
    val license_derivative_source_url: String,
    val license_object_url: String,
    val license_title: String,
    val name: String,
    val notes: List<Any>,
    val uuid: String
)