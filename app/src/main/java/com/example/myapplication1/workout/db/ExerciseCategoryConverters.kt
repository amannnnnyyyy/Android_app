package com.example.myapplication1.workout.db

import androidx.room.TypeConverter
import com.example.myapplication1.news.models.Source
import com.example.myapplication1.workout.models.ResultX
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ExerciseCategoryConverters {
    private val gson = Gson()

    @TypeConverter
    fun fromResultXList(value: List<ResultX>?): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toResultXList(value: String): List<ResultX>? {
        val listType = object : TypeToken<List<ResultX>>() {}.type
        return gson.fromJson(value, listType)
    }
}