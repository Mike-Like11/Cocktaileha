package com.example.cocktaileha.ui.screens.savedcocktaillist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.cocktaileha.data.model.Cocktail
import com.example.cocktaileha.ui.screens.cocktaillist.CocktailListViewModel
import com.example.cocktaileha.utils.DataState
import com.skydoves.landscapist.components.rememberImageComponent
import com.skydoves.landscapist.fresco.FrescoImage
import com.skydoves.landscapist.placeholder.shimmer.ShimmerPlugin
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SavedCocktailListScreen(navController: NavController) {
    val savedCocktailListViewModel = hiltViewModel<SavedCocktailListViewModel>()
    val refreshScope = rememberCoroutineScope()
    var refreshing by remember { mutableStateOf(false) }
    fun refresh() = refreshScope.launch {
        refreshing = true
        savedCocktailListViewModel.getSavedCocktails()
        when (savedCocktailListViewModel.cocktails.value) {
            is DataState.Success -> refreshing =false
            else -> {}
        }
    }

    var states: SnapshotStateList<Boolean>
    val state = rememberPullRefreshState(refreshing, ::refresh)
    LaunchedEffect(true) {
        savedCocktailListViewModel.getSavedCocktails()
    }
    Box(
        modifier = Modifier.fillMaxSize().pullRefresh(state),
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.Top
        ) {
            when (savedCocktailListViewModel.cocktails.value) {
                DataState.Loading -> {
                    item {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Bottom,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
                is DataState.Error -> {
                    item {
                        Text(savedCocktailListViewModel.cocktails.value.toString())
                    }
                }
                is DataState.Success<*> -> {
                    states =
                        SnapshotStateList<Boolean>().also {
                            (savedCocktailListViewModel.cocktails.value as DataState.Success<List<Cocktail>>).data.forEach {cocktail->
                                it.add(cocktail.isSaved)
                            }

                        }
                    items((savedCocktailListViewModel.cocktails.value as DataState.Success<List<Cocktail>>).data) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .padding(8.dp)
                                .clickable {
                                    navController.navigate("cocktailDetail/${it.idDrink}")
                                }
                                .fillMaxWidth()
                                .background(
                                    shape = RoundedCornerShape(16.dp),
                                    color = Color.White
                                ).padding(8.dp),
                        ) {
                            FrescoImage(
                                imageUrl = it.strDrinkThumb ?: "",
                                Modifier.fillMaxWidth().padding(),
                                component = rememberImageComponent {
                                    +ShimmerPlugin(
                                        baseColor = Color.Black,
                                        highlightColor = Color.Yellow
                                    )
                                },
                            )
                            it.strDrink?.let { name->
                                Text(
                                    text = name,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(8.dp),
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                it.strCategory?.let { name->
                                    Text(
                                        text = name,
                                    )
                                }
                                Icon(
                                    imageVector = Icons.Rounded.Favorite,
                                    tint = if(states.get((savedCocktailListViewModel.cocktails.value as DataState.Success<List<Cocktail>>).data.indexOf(it))) Color.Red else Color.Black,
                                    contentDescription = "Icon",
                                    modifier = Modifier.clickable {
                                        savedCocktailListViewModel.changeSaveStateCocktail(it)
                                        states[(savedCocktailListViewModel.cocktails.value as DataState.Success<List<Cocktail>>).data.indexOf(it)] =
                                            states.get((savedCocktailListViewModel.cocktails.value as DataState.Success<List<Cocktail>>).data.indexOf(it)).not()
                                    }
                                )
                            }
                        }
                    }
                }
                else -> {}
            }
        }
        PullRefreshIndicator(refreshing, state, Modifier.align(Alignment.TopCenter))
    }
}
