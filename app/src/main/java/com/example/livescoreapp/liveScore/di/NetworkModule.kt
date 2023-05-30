package com.example.livescoreapp.liveScore.di

import com.example.livescoreapp.liveScore.core.BASE_URL
import com.example.livescoreapp.liveScore.core.RequestInterceptor
import com.example.livescoreapp.liveScore.data.remote.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun okHTTP(): OkHttpClient{

        val logginInterceptor = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
        return OkHttpClient.Builder().
                addInterceptor(logginInterceptor)
            .addInterceptor(RequestInterceptor())
            .build()
    }

    @Provides
    fun retrofit(okHttpClient: OkHttpClient):Retrofit{
        return Retrofit.Builder().
                client(okHttpClient).
                addConverterFactory(GsonConverterFactory.create()).
                baseUrl(BASE_URL).
                build()
    }

    @Provides
    fun elenaAPiService(retrofit: Retrofit):ApiService{
        return retrofit.create(ApiService::class.java )
    }
}