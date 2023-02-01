package com.example.cocktaileha.di

import com.example.cocktaileha.data.repository.CocktailRepository
import com.example.cocktaileha.data.repository.CocktailRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun provideCocktailRepository(cocktailRepositoryImpl: CocktailRepositoryImpl): CocktailRepository
}