package com.example.app_loja_de_bolos.cake_category_list.presentation

import CakeCategoryAction
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_loja_de_bolos.cake_category_list.data.CakeCategoryListRepository
import com.example.app_loja_de_bolos.cake_list.presentation.CakeListAction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CakeCategoryListViewModel(
    private val cakeCategoryListRepository: CakeCategoryListRepository
) : ViewModel() {

    private val _categories = MutableStateFlow<List<String>>(emptyList())
    val categories: StateFlow<List<String>> = _categories

    private val _uiAction = MutableStateFlow<CakeCategoryAction?>(null)
    val uiAction: StateFlow<CakeCategoryAction?> = _uiAction

    fun fetchCakeCategories(cakeType: String?) {
        viewModelScope.launch {
            _uiAction.emit(CakeCategoryAction.UiState(isLoading = true))
            try {
                val categories = cakeCategoryListRepository.getCakeCategories(cakeType)
                _uiAction.emit(CakeCategoryAction.UpdateCategoryList(categories))
                _uiAction.emit(CakeCategoryAction.UiState(isLoading = false))
            } catch (e: Exception) {
                Log.e("CakeCategoryListViewModel", "Error fetching categories", e)
                _uiAction.emit(CakeCategoryAction.UiState(isLoading = false))
                _uiAction.emit(CakeCategoryAction.ShowErrorMsg)
            }
        }
    }

    fun onClickCakeCategory(category: String) {
        _uiAction.value = CakeCategoryAction.NavigateToCakeList(category)
    }

    fun resetUiAction() {
        _uiAction.value = null
    }
}