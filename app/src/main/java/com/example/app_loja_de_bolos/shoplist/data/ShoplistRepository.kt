package com.example.app_loja_de_bolos.shoplist.data

import com.example.app_loja_de_bolos.shoplist.model.CartItem
import kotlinx.coroutines.flow.Flow

interface ShoplistRepository {
    fun getCartItems(): Flow<List<CartItem>>
    suspend fun addItemToCart(item: CartItem)
    suspend fun removeItemFromCart(itemId: String)
    suspend fun clearCart()
    suspend fun getCartTotal(): Flow<Double>
}
