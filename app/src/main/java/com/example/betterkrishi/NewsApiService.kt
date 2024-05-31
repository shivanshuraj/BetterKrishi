package com.example.betterkrishi

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = "c83c7b4800d9406aa5148724ecd3dc43"

interface NewsApiService {
    @GET("v2/everything")
    suspend fun getAgriculturalNews(
        @Query("q") query: String = "agriculture",
        @Query("apiKey") apiKey: String = API_KEY
    ): NewsResponse
}

object RetrofitInstance {
    val api: NewsApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://newsapi.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApiService::class.java)
    }
}
