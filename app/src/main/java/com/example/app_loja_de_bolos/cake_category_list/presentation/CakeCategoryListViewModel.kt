package com.example.app_loja_de_bolos.cake_category_list.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_loja_de_bolos.cake_category_list.data.CakeCategoryListRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CakeCategoryListViewModel(
    private val cakeCategoryListRepository: CakeCategoryListRepository
) : ViewModel() {

    private val _categories = MutableStateFlow<List<String>>(emptyList())
    val categories: StateFlow<List<String>> = _categories

    private val _uiAction = MutableStateFlow<String?>(null)
    val uiAction: StateFlow<String?> = _uiAction

    fun fetchCakeCategories(cakeType: String?) {
        viewModelScope.launch {
            try {
                val categoriesList = cakeCategoryListRepository.getCakeCategories(cakeType)
                Log.d("CakeListViewModel", "Fetched categories: $categoriesList")
                _categories.value = categoriesList
            } catch (e: Exception) {
                Log.e("CakeListViewModel", "Error fetching categories", e)
                _uiAction.value = "Erro ao buscar categorias"
            }
        }
    }

    fun resetUiAction() {
        _uiAction.value = null
    }
}