package com.prajwal.stalenews.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.prajwal.stalenews.data.models.ArticlesItem
import com.prajwal.stalenews.domain.usecases.TopHeadlinesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsVM @Inject constructor(private val topHeadlinesUseCase: TopHeadlinesUseCase) :
    ViewModel() {
    private val news: MutableLiveData<List<ArticlesItem?>> = MutableLiveData()
    var newsLV: LiveData<List<ArticlesItem?>> = news

    fun getTopHeadlines() {
        CoroutineScope(Dispatchers.IO).launch {
            news.postValue(topHeadlinesUseCase.getTopHeadlines())
        }
    }

    fun selectCategory(category: String?) {
        CoroutineScope(Dispatchers.IO).launch {
            news.postValue(topHeadlinesUseCase.getTopHeadlines(category))
        }
    }
}