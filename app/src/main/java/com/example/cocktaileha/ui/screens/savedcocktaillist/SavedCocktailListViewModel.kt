package com.example.cocktaileha.ui.screens.savedcocktaillist

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
class SavedCocktailListViewModel @Inject constructor(private val cocktailRepository: CocktailRepository): ViewModel() {
    val cocktails: MutableState<DataState<List<Cocktail>>?> = mutableStateOf(null)
    fun getSavedCocktails() = viewModelScope.launch {
        cocktailRepository.getSavedCocktails().collect{
            cocktails.value = it
        }
    }
    fun changeSaveStateCocktail(cocktail: Cocktail) = viewModelScope.launch {
        cocktail.isSaved = cocktail.isSaved.not()
        cocktailRepository.saveCocktail(cocktail)
        getSavedCocktails()
    }
}