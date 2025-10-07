package com.example.myapplication1.workout.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.myapplication1.workout.models.ExerciseInfo

@Database([ExerciseInfo::class], version = 1)
@TypeConverters(ExerciseInfoConverters::class)
abstract class ExerciseInfoDatabase: RoomDatabase() {
    abstract fun exerciseInfoDao(): ExerciseInfoDao

    companion object {
        @Volatile
        private var INSTANCE: ExerciseInfoDatabase? = null

        fun getDatabase(context: Context): ExerciseInfoDatabase{
            val tempInstance = INSTANCE

            if (tempInstance!=null) return tempInstance

            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ExerciseInfoDatabase::class.java,
                    "exercise_info_db.db"
                ).build()

                INSTANCE = instance
                return instance
            }
        }
    }
}