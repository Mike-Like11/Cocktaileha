package com.example.cocktaileha.ui.screens.cocktaillist

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cocktaileha.data.model.Cocktail
import com.example.cocktaileha.data.repository.CocktailRepository
import com.example.cocktaileha.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CocktailListViewModel @Inject constructor(private val cocktailRepository: CocktailRepository): ViewModel() {
    val cocktails: MutableState<DataState<List<Cocktail>>?> = mutableStateOf(null)
    fun getCocktails(name:String) = viewModelScope.launch {
        cocktailRepository.getCocktails(name).collect{
            cocktails.value = it
            }
        }
    fun changeSaveStateCocktail(cocktail: Cocktail) = viewModelScope.launch {
        cocktail.isSaved = cocktail.isSaved.not()
        cocktailRepository.saveCocktail(cocktail)
    }
}