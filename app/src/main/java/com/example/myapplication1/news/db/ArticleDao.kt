package com.example.myapplication1.news.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplication1.news.models.Article
import kotlinx.coroutines.flow.StateFlow

@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveArticle(article: Article): Long

    @Query("SELECT * FROM articles ORDER BY id ASC")
    fun getAllArticles(): LiveData<List<Article>>


    @Delete
    suspend fun deleteArticle(article:Article)
}