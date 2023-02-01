package com.example.cocktaileha.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.navArgument
import com.example.cocktaileha.ui.screens.cocktaildetail.CocktailDetail
import com.example.cocktaileha.ui.screens.cocktaillist.CocktailListScreen
import com.example.cocktaileha.ui.screens.savedcocktaillist.SavedCocktailListScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Navigation(
    navController: NavHostController
) {
    AnimatedNavHost(navController = navController, startDestination = NavScreen.CocktailList.route) {
        composable(NavScreen.CocktailList.route) {
            CocktailListScreen(navController)
        }
        composable(NavScreen.SavedCocktailList.route) {
            SavedCocktailListScreen(navController = navController)
        }
        composable(
            NavScreen.CocktailDetail.route.plus(NavScreen.CocktailDetail.objectPath),
                    arguments = listOf(navArgument(NavScreen.CocktailDetail.objectName) {
                type = NavType.StringType
                nullable = true
            }),
            enterTransition = {
                slideIntoContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(700))
            },
            exitTransition = {
                slideOutOfContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(700))
            }
        ) {
            val genreId = it.arguments?.getString(NavScreen.CocktailDetail.objectName)
            genreId?.let {
                CocktailDetail(genreId)
            }
        }
    }

}