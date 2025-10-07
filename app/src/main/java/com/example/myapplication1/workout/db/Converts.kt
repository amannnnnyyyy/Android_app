package com.example.myapplication1.workout.db

import androidx.room.TypeConverter
import com.example.myapplication1.workout.models.Result

class Converts {
    @TypeConverter
    fun fromResult(result: List<Result>):String{
        return result[0].name?:"Unknkown"
    }

    @TypeConverter
    fun toResult(name: String): List<Result>{
        return listOf(Result(name, name, name, false, 1, false,false,name,name))
    }
}