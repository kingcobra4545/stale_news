package com.prajwal.stalenews.data.repo

import com.prajwal.stalenews.data.models.ArticlesItem

interface NewsDataRepo {
    suspend fun allNewsItems(category:String?): List<ArticlesItem?>
}