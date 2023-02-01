package com.example.cocktaileha

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.cocktaileha.navigation.Navigation
import com.example.cocktaileha.navigation.bottomNavItems
import com.example.cocktaileha.ui.theme.CocktailehaTheme
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberAnimatedNavController()
            CocktailehaTheme {
                // A surface container using the 'background' color from the theme
                val backStackEntry = navController.currentBackStackEntryAsState()

                Scaffold(
                    bottomBar = {
                        NavigationBar(
                        ) {
                            bottomNavItems.forEach { item ->
                                val selected =
                                    item.route == backStackEntry.value?.destination?.route

                                NavigationBarItem(
                                    selected = selected,
                                    onClick = { navController.navigate(item.route) },
                                    label = {
                                        Text(
                                            text = item.name,
                                            fontWeight = FontWeight.SemiBold,
                                        )
                                    },
                                    icon = {
                                        Icon(
                                            imageVector = item.icon,
                                            contentDescription = "${item.name} Icon",
                                        )
                                    }
                                )
                            }
                        }
                    },
                    content = {
                        Surface(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(it),
                            color = Color.LightGray
                        ) {
                            Navigation(navController = navController)
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CocktailehaTheme {
        Greeting("Android")
    }
}