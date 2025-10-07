package com.example.myapplication1.workout.db

import androidx.room.TypeConverter
import com.example.myapplication1.workout.models.Category
import com.example.myapplication1.workout.models.Equipment
import com.example.myapplication1.workout.models.Images
import com.example.myapplication1.workout.models.License
import com.example.myapplication1.workout.models.Muscle
import com.example.myapplication1.workout.models.MusclesSecondary
import com.example.myapplication1.workout.models.Translation
import com.example.myapplication1.workout.models.Video
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ExerciseInfoConverters {
    private val gson = Gson()

    @TypeConverter
    fun fromMuscleList(value: List<Muscle>?): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toMuscleList(value: String): List<Muscle>? {
        val listType = object : TypeToken<List<Muscle>>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun fromLicense(value: License): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toLicense(value: String): License? {
        return License(value, 0, value, value)
    }

    @TypeConverter
    fun fromMuscleSecondaryList(value: List<MusclesSecondary>?): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toMuscleSecondaryList(value: String): List<MusclesSecondary>? {
        val listType = object : TypeToken<List<MusclesSecondary>>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun fromTotalAuthorsHistoryList(value: List<String>?): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toTotalAuthorsHistoryList(value: String): List<String>? {
        val listType = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, listType)
    }


    @TypeConverter
    fun fromCategory(value: Category): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toCategory(value: String): Category? {
        return Category(0, value,)
    }


    @TypeConverter
    fun fromEquipmentList(value: List<Equipment>?): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toEquipmentList(value: String): List<Equipment>? {
        val listType = object : TypeToken<List<Equipment>>() {}.type
        return gson.fromJson(value, listType)
    }


    @TypeConverter
    fun fromImageList(value: List<Images>?): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toImageList(value: String): List<Images>? {
        val listType = object : TypeToken<List<Images>>() {}.type
        return gson.fromJson(value, listType)
    }


    @TypeConverter
    fun fromVideoList(value: List<Video>?): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toVideoList(value: String): List<Video>? {
        val listType = object : TypeToken<List<Video>>() {}.type
        return gson.fromJson(value, listType)
    }


    @TypeConverter
    fun fromTranslationList(value: List<Translation>?): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toTranslationList(value: String): List<Translation>? {
        val listType = object : TypeToken<List<Translation>>() {}.type
        return gson.fromJson(value, listType)
    }


}