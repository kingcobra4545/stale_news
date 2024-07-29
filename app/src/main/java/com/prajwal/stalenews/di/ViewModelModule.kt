package com.prajwal.stalenews.di

import com.prajwal.stalenews.domain.usecases.TopHeadlinesUseCase
import com.prajwal.stalenews.presentation.viewmodels.NewsVM
import com.prajwal.stalenews.utils.Constants
import com.prajwal.stalenews.data.remote.RemoteAPIService
import com.prajwal.stalenews.data.remote.RemoteDataSourceImpl
import com.prajwal.stalenews.data.repo.NewsDataRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class ViewModelModule {
    @Provides
    fun providesRemoteNewsDataRepo(remoteAPIService: RemoteAPIService): NewsDataRepo =
        RemoteDataSourceImpl(remoteAPIService, Constants.NEWS_API_KEY)

    @Provides
    fun providesTopHeadlinesUseCase(repo: NewsDataRepo) = TopHeadlinesUseCase(repo)

    @Provides
    fun providesNewsVM(topHeadlinesUseCase: TopHeadlinesUseCase) = NewsVM(topHeadlinesUseCase)
}