package com.example.app_loja_de_bolos.shopping_list.data.remote

import com.example.app_loja_de_bolos.shopping_list.model.CartItem

interface ShoppingListRemoteDatasource {
    suspend fun fetchCartItems(): List<CartItem>
    suspend fun updateCart(items: List<CartItem>)
    suspend fun clearCart()
    suspend fun deleteItem(itemId: String)
}
