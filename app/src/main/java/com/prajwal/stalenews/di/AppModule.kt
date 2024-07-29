package com.prajwal.stalenews.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.prajwal.stalenews.data.remote.RemoteAPIService
import com.prajwal.stalenews.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    fun provideBaseUrl(): String = Constants.BASE_URL

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val clientBuilder = OkHttpClient.Builder()
        return clientBuilder.addInterceptor { chain ->
            val request = chain.request()
            val response = chain.proceed(request)
            response
        }.addInterceptor(logging).build()
    }

    @Provides
    fun provideRetrofit(
        baseUrl: String,
        gson: Gson,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    fun provideRemoteApiService(retrofit: Retrofit): RemoteAPIService =
        retrofit.create(RemoteAPIService::class.java)
}