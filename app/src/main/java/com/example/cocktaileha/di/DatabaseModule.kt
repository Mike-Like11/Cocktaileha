package com.example.cocktaileha.di

import android.content.Context
import androidx.room.Room
import com.example.cocktaileha.CocktailehaApp
import com.example.cocktaileha.data.local.CocktailDao
import com.example.cocktaileha.data.local.CocktailDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context): CocktailDatabase {
        return Room.databaseBuilder(appContext, CocktailDatabase::class.java, "cocktail-db").fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun providesNewsDao(cocktailDatabase: CocktailDatabase): CocktailDao {
        return cocktailDatabase.getCocktailDao()
    }
}