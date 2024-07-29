package com.prajwal.stalenews.data.remote

import com.prajwal.stalenews.data.models.ArticlesItem
import com.prajwal.stalenews.data.repo.NewsDataRepo
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val remoteAPIService: RemoteAPIService,
    private val apiKey: String
) : NewsDataRepo {
    override suspend fun allNewsItems(category: String?): List<ArticlesItem?> =
        remoteAPIService.getTopHeadlines(apiKey, category).articles ?: emptyList()
}