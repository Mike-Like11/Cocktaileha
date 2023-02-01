package com.example.cocktaileha.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cocktaileha.data.model.Cocktail

@Database(entities = [Cocktail::class], version = 3)
abstract class CocktailDatabase: RoomDatabase() {
    abstract fun getCocktailDao(): CocktailDao
}