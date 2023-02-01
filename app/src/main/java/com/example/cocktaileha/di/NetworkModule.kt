package com.example.cocktaileha.di

import com.example.cocktaileha.data.remote.ApiService
import com.example.cocktaileha.data.repository.CocktailRepository
import com.example.cocktaileha.data.repository.CocktailRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun providesBaseUrl():String  = "https://thecocktaildb.com/api/json/v1/1/"

    @Provides
    @Singleton
    fun provideRetrofit(BASE_URL : String): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()
    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit):ApiService = retrofit.create(ApiService::class.java)
//    @Provides
//    @Singleton
//    fun provideCocktailRepository(apiService: ApiService):CocktailRepository = CocktailRepositoryImpl(apiService)
}