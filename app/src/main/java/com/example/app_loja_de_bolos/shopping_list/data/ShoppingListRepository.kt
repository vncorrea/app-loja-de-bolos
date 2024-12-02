package com.example.app_loja_de_bolos.shopping_list.data

import com.example.app_loja_de_bolos.shopping_list.model.CartItem
import kotlinx.coroutines.flow.Flow

interface ShoppingListRepository {
    suspend fun fetchCartItems(): List<CartItem>
    suspend fun updateCart(items: List<CartItem>)
    suspend fun updateItem(cartItem: CartItem)
    suspend fun clearCart()
    suspend fun deleteItem(itemId: String)
}
