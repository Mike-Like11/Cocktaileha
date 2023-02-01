package com.example.cocktaileha.ui.screens.cocktaillist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.cocktaileha.data.model.Cocktail
import com.example.cocktaileha.utils.DataState
import com.skydoves.landscapist.components.rememberImageComponent
import com.skydoves.landscapist.fresco.FrescoImage
import com.skydoves.landscapist.placeholder.shimmer.ShimmerPlugin
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CocktailListScreen(navController: NavController) {
    val cocktailListViewModel = hiltViewModel<CocktailListViewModel>()
    val refreshScope = rememberCoroutineScope()
    var refreshing by remember { mutableStateOf(false) }
    var states = remember {
        SnapshotStateList<Boolean>()}
    val textState = rememberSaveable {
        mutableStateOf("")
    }
    LaunchedEffect(true) {
        cocktailListViewModel.getCocktails(textState.value)
    }
    fun refresh() = refreshScope.launch {
        refreshing = true
        cocktailListViewModel.getCocktails(textState.value)
        refreshing = false
    }
    val state = rememberPullRefreshState(refreshing, ::refresh)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(state),
        contentAlignment = Alignment.TopCenter
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.Center,
        ) {
            item {
                BasicTextField(
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 8.dp)
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .clip(shape = RoundedCornerShape(16.dp))
                        .background(Color.White),
                    singleLine = true,
                    decorationBox = { innerTextField ->
                        Row(
                            modifier = Modifier // margin left and right
                                .fillMaxWidth()
                                .padding(all = 8.dp), // inner padding
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Favorite icon",
                                tint = Color.DarkGray
                            )
                            Spacer(modifier = Modifier.width(width = 8.dp))
                            innerTextField()
                        }
                    },
                    value = textState.value,
                    onValueChange = {
                        textState.value = it
                        cocktailListViewModel.getCocktails(textState.value)
                    }
                )
            }
            when (cocktailListViewModel.cocktails.value) {
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
                        Text(cocktailListViewModel.cocktails.value.toString())
                    }
                }
                is DataState.Success<*> -> {
                    states =
                        SnapshotStateList<Boolean>().also {
                            (cocktailListViewModel.cocktails.value as DataState.Success<List<Cocktail>>).data.forEach {cocktail->
                                it.add(cocktail.isSaved)
                            }

                    }
                    items((cocktailListViewModel.cocktails.value as DataState.Success<List<Cocktail>>).data) {
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
                                )
                                .padding(8.dp),
                        ) {
                            FrescoImage(
                                imageUrl = it.strDrinkThumb ?: "",
                                Modifier
                                    .fillMaxWidth()
                                    .padding(),
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
                                    tint = if(states.get((cocktailListViewModel.cocktails.value as DataState.Success<List<Cocktail>>).data.indexOf(it))) Color.Red else Color.Black,
                                    contentDescription = "Icon",
                                    modifier = Modifier.clickable {
                                        cocktailListViewModel.changeSaveStateCocktail(it)
                                        states[(cocktailListViewModel.cocktails.value as DataState.Success<List<Cocktail>>).data.indexOf(it)] =
                                            states.get((cocktailListViewModel.cocktails.value as DataState.Success<List<Cocktail>>).data.indexOf(it)).not()
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
