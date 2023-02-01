package com.example.cocktaileha.data.local

import androidx.room.*
import com.example.cocktaileha.data.model.Cocktail

@Dao
interface CocktailDao {
    @Query("SELECT * from cocktails")
    fun getCocktails():List<Cocktail>
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(cocktails: List<Cocktail>)
    @Query("SELECT * from cocktails WHERE idDrink=:idCocktail")
    fun getCocktailById(idCocktail: String): Cocktail
    @Update
    suspend fun updateCocktail(cocktail: Cocktail)
}