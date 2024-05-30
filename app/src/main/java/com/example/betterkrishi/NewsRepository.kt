package com.example.betterkrishi

class NewsRepository(private val apiService: NewsApiService) {
    suspend fun fetchTopHeadlines(country: String, apiKey: String): List<NewsArticle> {
        return apiService.getTopHeadlines(country, apiKey).articles
    }
}
