package com.example.app_loja_de_bolos.shoplist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_loja_de_bolos.shoplist.data.ShoplistRepository
import com.example.app_loja_de_bolos.shoplist.model.CartItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ShoplistViewModel(private val shoplistRepository: ShoplistRepository) : ViewModel() {

    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> get() = _cartItems

    private val _cartTotal = MutableStateFlow(0.0)
    val cartTotal: StateFlow<Double> get() = _cartTotal

    init {
        fetchCartItems()
        calculateTotal()
    }

    fun fetchCartItems() {
        viewModelScope.launch {
            shoplistRepository.getCartItems().collect { items ->
                _cartItems.value = items
            }
        }
    }

    fun addItemToCart(item: CartItem) {
        viewModelScope.launch {
            shoplistRepository.addItemToCart(item)
        }
    }

    fun removeItemFromCart(itemId: String) {
        viewModelScope.launch {
            shoplistRepository.removeItemFromCart(itemId)
        }
    }

    fun clearCart() {
        viewModelScope.launch {
            shoplistRepository.clearCart()
        }
    }

    private fun calculateTotal() {
        viewModelScope.launch {
            shoplistRepository.getCartTotal().collect { total ->
                _cartTotal.value = total
            }
        }
    }
}
