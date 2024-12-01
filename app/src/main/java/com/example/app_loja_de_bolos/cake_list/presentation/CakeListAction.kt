package com.example.app_loja_de_bolos.cake_list.presentation

import com.example.app_loja_de_bolos.cake_category_list.model.CakeCategory
import com.example.app_loja_de_bolos.cake_list.model.Cake

sealed class CakeListAction {
    data class UpdateCakeList(val cakes: List<Cake>) : CakeListAction()
    object ShowErrorMsg : CakeListAction()
    data class NavigateToCakeDetails(val cake: Cake) : CakeListAction()
    data class UiState (val isLoading: Boolean) : CakeListAction()
}