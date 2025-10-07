package com.example.myapplication1.workout.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.myapplication1.news.db.ArticleDao
import com.example.myapplication1.news.db.ArticleDatabase
import com.example.myapplication1.workout.models.Routine

@Database([Routine::class], version = 1)
@TypeConverters(Converts::class)
abstract class RoutineDatabase: RoomDatabase() {
    abstract fun routineDao(): RoutineDao

    companion object {
        @Volatile
        private var INSTANCE: RoutineDatabase? = null

        fun getDatabase(context: Context): RoutineDatabase{
            val tempInstance = INSTANCE

            if (tempInstance!=null) return tempInstance

            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RoutineDatabase::class.java,
                    "routine_db.db"
                ).build()

                INSTANCE = instance
                return instance
            }
        }
    }
}