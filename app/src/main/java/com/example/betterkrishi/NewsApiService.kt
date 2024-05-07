package com.example.betterkrishi

import retrofit2.http.GET

interface NewsApiService {
    @GET("news")
    suspend fun getAllNews(): List<NewsArticle>
}

class NewsRepository(private val apiService: NewsApiService) {
    suspend fun fetchNews(): List<NewsArticle> = apiService.getAllNews()
}
