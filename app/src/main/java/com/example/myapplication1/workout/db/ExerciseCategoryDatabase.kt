package com.example.myapplication1.workout.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.myapplication1.workout.models.ExerciseCategory

@Database([ExerciseCategory::class], version = 1)
@TypeConverters(ExerciseCategoryConverters::class)
abstract class ExerciseCategoryDatabase: RoomDatabase() {
    abstract fun exerciseCategoryDao(): ExerciseCategoryDao

    companion object {
        @Volatile
        private var INSTANCE: ExerciseCategoryDatabase? = null

        fun getDatabase(context: Context): ExerciseCategoryDatabase{
            val tempInstance = INSTANCE

            if (tempInstance!=null) return tempInstance

            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ExerciseCategoryDatabase::class.java,
                    "exercise_category_db.db"
                ).build()

                INSTANCE = instance
                return instance
            }
        }
    }
}