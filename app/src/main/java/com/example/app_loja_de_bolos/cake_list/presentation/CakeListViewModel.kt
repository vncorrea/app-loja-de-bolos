package com.example.app_loja_de_bolos.cake_list.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_loja_de_bolos.cake_category_list.model.CakeCategory
import com.example.app_loja_de_bolos.cake_list.data.CakeListRepository
import com.example.app_loja_de_bolos.cake_list.model.Cake
import com.example.app_loja_de_bolos.home.model.CakeType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CakeListViewModel(
    private val cakeListRepository: CakeListRepository
) : ViewModel() {

    private val _cakes = MutableStateFlow<List<String>>(emptyList())
    val cakes: StateFlow<List<String>> = _cakes

    private val _uiAction = MutableStateFlow<CakeListAction?>(null)
    val uiAction: StateFlow<CakeListAction?> = _uiAction

    fun fetchCakeList(cakeType: String?, cakeCategory: String?) {
        viewModelScope.launch {
            _uiAction.emit(CakeListAction.UiState(isLoading = true))
            try {
                val cakes = cakeListRepository.getCakesByCategory(cakeType, cakeCategory)
                _uiAction.emit(CakeListAction.UpdateCakeList(cakes))
                _uiAction.emit(CakeListAction.UiState(isLoading = false))
            } catch (e: Exception) {
                Log.e("CakeCategoryListViewModel", "Error fetching categories", e)
                _uiAction.emit(CakeListAction.UiState(isLoading = false))
                _uiAction.emit(CakeListAction.ShowErrorMsg)
            }
        }
    }

    fun onClickCake(cake: Cake) {
        _uiAction.value = CakeListAction.NavigateToCakeDetails(cake)
    }

    fun resetUiAction() {
        _uiAction.value = null
    }
}