package com.example.betterkrishi

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class NewsViewModel(private val repository: NewsRepository) : ViewModel() {

    val newsList = MutableLiveData<List<NewsArticle>>()
    val isLoading = MutableLiveData<Boolean>()

    fun fetchTopHeadlines(country: String, apiKey: String) {
        viewModelScope.launch {
            isLoading.value = true
            try {
                val articles = repository.fetchTopHeadlines(country, apiKey)
                newsList.value = articles
            } catch (e: Exception) {
                // Handle error
            } finally {
                isLoading.value = false
            }
        }
    }
}
