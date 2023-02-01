package com.example.cocktaileha.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Search
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem (
      val name:String,
      val route: String,
      val icon:ImageVector
)
val bottomNavItems = listOf(
    BottomNavItem(
        name = "Cocktail search",
        route = "cocktailList",
        icon = Icons.Rounded.Search,
    ),
    BottomNavItem(
        name = "Saved",
        route = "savedCocktailList",
        icon = Icons.Rounded.Home,
    ),
)