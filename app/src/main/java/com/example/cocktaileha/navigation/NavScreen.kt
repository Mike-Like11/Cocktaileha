package com.example.cocktaileha.navigation

sealed class NavScreen(
    val route: String,
    val objectPath: String = "",
    val objectName: String = ""
){
    object CocktailList: NavScreen("cocktailList")
    object SavedCocktailList: NavScreen("savedCocktailList")
    object CocktailDetail: NavScreen("cocktailDetail",objectPath = "/{cocktailId}", objectName = "cocktailId")
}
