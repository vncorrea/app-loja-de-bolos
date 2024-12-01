package com.example.app_loja_de_bolos.shoplist.data

import com.example.app_loja_de_bolos.shoplist.model.CartItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class ShoplistRepositoryImpl : ShoplistRepository {

    private val cartItems = MutableStateFlow<List<CartItem>>(emptyList())

    override fun getCartItems(): Flow<List<CartItem>> = cartItems

    override suspend fun addItemToCart(item: CartItem) {
        val currentItems = cartItems.value.toMutableList()
        currentItems.add(item)
        cartItems.value = currentItems
    }

    override suspend fun removeItemFromCart(itemId: String) {
        val currentItems = cartItems.value.toMutableList()
        currentItems.removeAll { it.id == itemId }
        cartItems.value = currentItems
    }

    override suspend fun clearCart() {
        cartItems.value = emptyList()
    }

    override suspend fun getCartTotal(): Flow<Double> {
        return cartItems.map { items ->
            items.sumOf { it.price * it.quantity }
        }
    }
}
