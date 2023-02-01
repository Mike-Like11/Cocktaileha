package com.example.cocktaileha.data.remote

import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {
    @GET("search.php")
    suspend fun getCocktails(@Query("s") s:String): NetworkResponse
    @GET("lookup.php")
    suspend fun getCocktailInfo(@Query("i") id:String="11007"): NetworkResponse
    @GET("random.php")
    suspend fun getRandomCocktail(): NetworkResponse
}