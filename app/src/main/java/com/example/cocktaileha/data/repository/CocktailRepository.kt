package com.example.cocktaileha.data.repository

import com.example.cocktaileha.data.model.Cocktail
import com.example.cocktaileha.utils.DataState
import kotlinx.coroutines.flow.Flow

interface CocktailRepository {
    suspend fun getCocktails(name:String): Flow<DataState<List<Cocktail>>>
    suspend fun getCocktailInfo(id:String): Flow<DataState<List<Cocktail>>>
    suspend fun getSavedCocktails(): Flow<DataState<List<Cocktail>>>
    suspend fun saveCocktail(cocktail: Cocktail)
}