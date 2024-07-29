package com.prajwal.stalenews.domain.usecases

import com.prajwal.stalenews.data.repo.NewsDataRepo
import javax.inject.Inject

class TopHeadlinesUseCase @Inject constructor(private val repo: NewsDataRepo) {
    suspend fun getTopHeadlines(category: String? = null) = repo.allNewsItems(category)
}