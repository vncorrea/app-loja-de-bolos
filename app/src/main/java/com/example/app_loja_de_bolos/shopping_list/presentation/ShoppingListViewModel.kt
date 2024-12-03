package com.example.app_loja_de_bolos.shopping_list.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_loja_de_bolos.cake_list.model.Cake
import com.example.app_loja_de_bolos.cake_list.presentation.CakeListAction
import com.example.app_loja_de_bolos.shopping_list.data.ShoppingListRepository
import com.example.app_loja_de_bolos.shopping_list.model.CartItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class ShoppingListViewModel(
    private val shoppingListRepository: ShoppingListRepository
) : ViewModel() {
    private val _cartItems = MutableStateFlow<List<String>>(emptyList())
    val cartItems: StateFlow<List<String>> = _cartItems

    private val _uiAction = MutableStateFlow<ShoppingListAction?>(null)
    val uiAction: StateFlow<ShoppingListAction?> = _uiAction

    private val _cartTotal = MutableStateFlow(0.0)
    val cartTotal: StateFlow<Double> = _cartTotal

    fun fetchShoppingList() {
        viewModelScope.launch {
            _uiAction.emit(ShoppingListAction.UiState(isLoading = true))
            try {
                val cartItems = shoppingListRepository.fetchCartItems()
                _uiAction.emit(ShoppingListAction.updateCartList(cartItems))
                calculateCartTotal(cartItems)
                _uiAction.emit(ShoppingListAction.UiState(isLoading = false))
            } catch (e: Exception) {
                Log.e("CakeCategoryListViewModel", "Error fetching categories", e)
                _uiAction.emit(ShoppingListAction.UiState(isLoading = false))
                _uiAction.emit(ShoppingListAction.ShowErrorMsg)
            }
        }
    }

    fun onDeleteItemClick(cartItemId: String) {
        viewModelScope.launch {
            try {
                shoppingListRepository.deleteItem(cartItemId)
                fetchShoppingList()
            } catch (e: Exception) {
                Log.e("ShoppingListViewModel", "Error deleting item", e)
                _uiAction.emit(ShoppingListAction.ShowErrorMsg)
            }
        }
    }

    private fun calculateCartTotal(items: List<CartItem>) {
        val total = items.sumOf { cartItem ->
            cartItem.value.replace("R$", "").replace(",", ".").trim().toDouble() * cartItem.quantity
        }
        _cartTotal.value = total
    }

    fun updateItemQuantity(cartItem: CartItem, newQuantity: Int) {
        viewModelScope.launch {
            try {
                val updatedItem = cartItem.copy(quantity = newQuantity)
                shoppingListRepository.updateItem(updatedItem)
                fetchShoppingList()
            } catch (e: Exception) {
                Log.e("ShoppingListViewModel", "Erro ao atualizar quantidade", e)
                _uiAction.emit(ShoppingListAction.ShowErrorMsg)
            }
        }
    }

    fun onCheckoutClick() {
        viewModelScope.launch {
            try {
                shoppingListRepository.clearCart();
                _uiAction.emit(ShoppingListAction.NavigateToHome())
                _uiAction.emit(ShoppingListAction.ShowSuccessMsg)
            } catch (e: Exception) {
                Log.e("ShoppingListViewModel", "Error deleting item", e)
                _uiAction.emit(ShoppingListAction.ShowErrorMsg)
            }
        }
    }

//    fun onClickCake(cake: Cake) {
//        _uiAction.value = CakeListAction.NavigateToCakeDetails(cake)
//    }

    fun resetUiAction() {
        _uiAction.value = null
    }
}