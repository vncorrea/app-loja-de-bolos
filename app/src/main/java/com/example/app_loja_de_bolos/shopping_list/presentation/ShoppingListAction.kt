package com.example.app_loja_de_bolos.shopping_list.presentation

import com.example.app_loja_de_bolos.shopping_list.model.CartItem
import kotlinx.coroutines.flow.StateFlow

sealed class ShoppingListAction {
    data class updateCartList(val cartItems: List<CartItem>) : ShoppingListAction()
    object ShowErrorMsg : ShoppingListAction()
    class NavigateToHome() : ShoppingListAction()
    data class UiState(val isLoading: Boolean) : ShoppingListAction()
    object ShowSuccessMsg : ShoppingListAction()
}
