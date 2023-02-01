package com.example.cocktaileha.ui.screens.cocktaildetail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cocktaileha.data.model.Cocktail
import com.example.cocktaileha.data.repository.CocktailRepository
import com.example.cocktaileha.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CocktailDetailViewModel @Inject constructor(private val cocktailRepository: CocktailRepository) : ViewModel() {
    val cocktailInfo: MutableState<DataState<List<Cocktail>>?> = mutableStateOf(null)
    fun getCocktailFullInfo(cocktailId: String) = viewModelScope.launch {
        cocktailRepository.getCocktailInfo(cocktailId).collect{
            cocktailInfo.value = it
        }
    }
}