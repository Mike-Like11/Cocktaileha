package com.example.cocktaileha.data.remote

import com.example.cocktaileha.data.model.Cocktail

data class NetworkResponse(
    var drinks : ArrayList<Cocktail> = arrayListOf()
)