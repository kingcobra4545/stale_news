package com.prajwal.stalenews.data.remote

import com.prajwal.stalenews.data.models.NewsHeadlinesArticles
import com.prajwal.stalenews.data.models.NewsHeadlinesResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface RemoteAPIService {
    @GET("v2/top-headlines/")
    suspend fun getTopHeadlines(
        @Query("apiKey") apiKey: String,
        @Query("category")category: String?,
        @Query("country") country: String?="us",
        ): NewsHeadlinesArticles
}