package com.example.myapplication1.news.repository

import com.example.myapplication1.news.api.RetrofitInstance
import com.example.myapplication1.news.db.ArticleDatabase
import com.example.myapplication1.news.models.Article

class NewsRepository(
    val db: ArticleDatabase
) {
    suspend fun getBreakingNews(countryCode: String, pageNumber: Int)= RetrofitInstance.api.getBreakingNews(countryCode, pageNumber)

    suspend fun searchNews(searchQuery: String, pageNumber:Int) = RetrofitInstance.api.searchNews(searchQuery, pageNumber)

    suspend fun saveArticle(article: Article) = db.articleDao().saveArticle(article)

    suspend fun getAllArticles() = db.articleDao().getAllArticles()

    suspend fun deleteArticle(article: Article) = db.articleDao().deleteArticle(article)
}