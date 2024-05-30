package com.example.betterkrishi.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.betterkrishi.NewsArticle
import com.example.betterkrishi.NewsViewModel

@Composable
fun NewsListScreen(viewModel: NewsViewModel, onNewsSelected: (NewsArticle) -> Unit) {
    val newsList by viewModel.newsList.observeAsState(initial = emptyList())
    val isLoading by viewModel.isLoading.observeAsState(true)

    if (isLoading) {
        CircularProgressIndicator() // Show loading indicator
    } else {
        LazyColumn {
            items(newsList) { news ->
                NewsItem(news, onNewsSelected)
            }
        }
    }
}

@Composable
fun NewsItem(news: NewsArticle, onNewsSelected: (NewsArticle) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onNewsSelected(news) }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = news.title, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = news.summary, style = MaterialTheme.typography.bodySmall)
        }
    }
}
