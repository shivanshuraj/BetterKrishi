package com.example.betterkrishi

data class NewsArticle(
    val title: String,
    val summary: String
)

data class NewsResponse(
    val articles: List<NewsArticle>
)
