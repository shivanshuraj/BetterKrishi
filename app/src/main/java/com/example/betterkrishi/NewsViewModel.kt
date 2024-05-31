package com.example.betterkrishi

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class NewsViewModel : ViewModel() {
    private val _news = MutableLiveData<List<Article>>()
    val news: LiveData<List<Article>> = _news

    init {
        fetchNews()
    }

    private fun fetchNews() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getAgriculturalNews()
                if (response.status == "ok") {
                    _news.value = response.articles
                }
            } catch (e: Exception) {
                // Handle the error
            }
        }
    }
}