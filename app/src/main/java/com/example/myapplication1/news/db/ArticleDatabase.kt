package com.example.myapplication1.news.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.myapplication1.news.models.Article

@Database(entities = [Article::class], version = 1)
@TypeConverters(Converters::class)
abstract class ArticleDatabase: RoomDatabase() {
    abstract fun articleDao(): ArticleDao

    companion object {
        @Volatile
        private var INSTANCE: ArticleDatabase? = null

        fun getDatabase(context: Context): ArticleDatabase{
            val tempInstance = INSTANCE

            if (tempInstance!=null) return tempInstance

            synchronized(this){
                val instance = Room.databaseBuilder(
                   context.applicationContext,
                    ArticleDatabase::class.java,
                    "article_db.db"
                ).build()

                INSTANCE = instance
                return instance
            }
        }
    }
}