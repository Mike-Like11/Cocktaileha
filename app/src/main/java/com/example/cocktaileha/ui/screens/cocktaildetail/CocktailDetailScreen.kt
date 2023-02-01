package com.example.cocktaileha.ui.screens.cocktaildetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cocktaileha.data.model.Cocktail
import com.example.cocktaileha.utils.DataState
import com.skydoves.landscapist.components.rememberImageComponent
import com.skydoves.landscapist.fresco.FrescoImage
import com.skydoves.landscapist.placeholder.shimmer.ShimmerPlugin

@Composable
fun CocktailDetail(cocktailId: String) {
    val cocktailDetailViewModel = hiltViewModel<CocktailDetailViewModel>()
    LaunchedEffect(true) {
        cocktailDetailViewModel.getCocktailFullInfo(cocktailId)
    }
    LazyColumn(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .background(
                shape = RoundedCornerShape(16.dp),
                color = Color.White
            )
            .padding(16.dp),
    ) {
        when (cocktailDetailViewModel.cocktailInfo.value) {
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
                    Text(cocktailDetailViewModel.cocktailInfo.value.toString())
                }
            }
            is DataState.Success<List<Cocktail>> -> {
                val cocktailInfo =
                    (cocktailDetailViewModel.cocktailInfo.value as DataState.Success<List<Cocktail>>).data.first()
                    item {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            FrescoImage(
                                imageUrl = (cocktailDetailViewModel.cocktailInfo.value as DataState.Success<List<Cocktail>>).data.first().strDrinkThumb,
                                component = rememberImageComponent {
                                    +ShimmerPlugin(
                                        baseColor = Color.Black,
                                        highlightColor = Color.Yellow
                                    )
                                },
                                modifier = Modifier.fillMaxWidth()
                            )
                            cocktailInfo.strDrink?.let {
                                Text(
                                    text = it,
                                    modifier = Modifier.padding(),
                                    textAlign = TextAlign.Center,
                                    fontSize = 30.sp
                                )
                            }

                            cocktailInfo.strTags?.let {
                                Text(
                                    text = it,
                                    modifier = Modifier.padding(),
                                    textAlign = TextAlign.Start
                                )
                            }
                            cocktailInfo.strAlcoholic?.let {
                                Text(
                                    text = it,
                                    modifier = Modifier.padding(),
                                    textAlign = TextAlign.Start
                                )
                            }
                            cocktailInfo.strCategory?.let {
                                Text(
                                    text = it,
                                    modifier = Modifier.padding(),
                                    textAlign = TextAlign.Start
                                )
                            }
                        }
                        Text(
                            text = "Ingridients",
                            modifier = Modifier.padding(),
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            fontSize = 24.sp
                        )
                        cocktailInfo.strIngredient1?.let {
                            Text(
                                text = it,
                                modifier = Modifier.padding(),
                                textAlign = TextAlign.Start
                            )
                        }
                        cocktailInfo.strIngredient2?.let {
                            Text(
                                text = it,
                                modifier = Modifier.padding()
                            )
                        }
                        cocktailInfo.strIngredient3?.let {
                            Text(
                                text = it,
                                modifier = Modifier.padding()
                            )
                        }
                        cocktailInfo.strIngredient4?.let {
                            Text(
                                text = it,
                                modifier = Modifier.padding()
                            )
                        }
                        cocktailInfo.strIngredient5?.let {
                            Text(
                                text = it,
                                modifier = Modifier.padding()
                            )
                        }
                        cocktailInfo.strIngredient6?.let {
                            Text(
                                text = it,
                                modifier = Modifier.padding()
                            )
                        }
                        cocktailInfo.strIngredient7?.let {
                            Text(
                                text = it,
                                modifier = Modifier.padding()
                            )
                        }
                        cocktailInfo.strIngredient8?.let {
                            Text(
                                text = it,
                                modifier = Modifier.padding()
                            )
                        }
                        cocktailInfo.strIngredient9?.let {
                            Text(
                                text = it,
                                modifier = Modifier.padding()
                            )
                        }
                        cocktailInfo.strIngredient10?.let {
                            Text(
                                text = it,
                                modifier = Modifier.padding()
                            )
                        }
                        cocktailInfo.strInstructions?.let {
                            Text(
                                text = "Instruction",
                                modifier = Modifier.padding(),
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                fontSize = 24.sp
                            )
                            Text(
                                text = it,
                                modifier = Modifier.padding(),
                                textAlign = TextAlign.Start
                            )
                        }
                    }
            }
            else -> {}
        }
    }
}