package com.example.betterkrishi

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class NewsViewModel(private val repository: NewsRepository): ViewModel() {
    var newsList = mutableStateOf<List<NewsArticle>>(listOf())
    var isLoading = mutableStateOf(true)

    init {
        fetchNews()
    }

    private fun fetchNews() {
        viewModelScope.launch {
            try {
                isLoading.value = true
                newsList.value = repository.fetchNews()
            } catch (e: Exception) {
                // Handle errors
            } finally {
                isLoading.value = false
            }
        }
    }
}
