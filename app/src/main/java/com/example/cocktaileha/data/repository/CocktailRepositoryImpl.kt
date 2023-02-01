package com.example.cocktaileha.data.repository

import android.util.Log
import androidx.compose.ui.text.toLowerCase
import com.example.cocktaileha.data.local.CocktailDao
import com.example.cocktaileha.data.model.Cocktail
import com.example.cocktaileha.data.remote.ApiService
import com.example.cocktaileha.utils.DataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

class CocktailRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val cocktailDao: CocktailDao
) : CocktailRepository {

    override suspend fun getCocktails(name:String): Flow<DataState<List<Cocktail>>> =
        withContext(Dispatchers.IO) {
            flow {
                emit(DataState.Loading)
                try {
                    if (name.equals("")) {
                        repeat(10) {
                            cocktailDao.insertAll(apiService.getRandomCocktail().drinks)
                        }
                    }
                    else{
                        cocktailDao.insertAll(apiService.getCocktails(name).drinks)
                    }
                    emit(DataState.Success(cocktailDao.getCocktails().filter { it.strDrink?.lowercase()?.contains(name.lowercase())?: false }))
                } catch (e: Exception) {
                    emit(DataState.Success(cocktailDao.getCocktails().filter { it.strDrink?.lowercase()?.contains(name.lowercase())?: false }))
                }
            }.flowOn(Dispatchers.IO)
        }

    override suspend fun getCocktailInfo(id: String): Flow<DataState<List<Cocktail>>> =
        withContext(Dispatchers.IO) {
            flow {
                emit(DataState.Loading)
                try {
                    cocktailDao.insertAll(apiService.getCocktailInfo(id).drinks)
                    emit(DataState.Success(listOf(cocktailDao.getCocktailById(id))))
                } catch (e: Exception) {
                    emit(DataState.Success(listOf(cocktailDao.getCocktailById(id))))
                }
            }.flowOn(Dispatchers.IO)
        }

    override suspend fun getSavedCocktails(): Flow<DataState<List<Cocktail>>> = withContext(Dispatchers.IO){
        flow {
            emit(DataState.Loading)
            try {
                emit(DataState.Success(cocktailDao.getCocktails().filter { it.isSaved }))
            } catch (e: Exception){
                emit(DataState.Error(e))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun saveCocktail(cocktail: Cocktail) = withContext(Dispatchers.IO){
        cocktailDao.updateCocktail(cocktail)
    }
}