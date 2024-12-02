package com.example.app_loja_de_bolos.shopping_list.data

import com.example.app_loja_de_bolos.cake_list.data.CakeListRepository
import com.example.app_loja_de_bolos.cake_list.data.remote.CakeListRemoteDatasource
import com.example.app_loja_de_bolos.shopping_list.data.remote.ShoppingListRemoteDatasource
import com.example.app_loja_de_bolos.shopping_list.model.CartItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class ShoppingListRepositoryImpl(
    private val remoteDatasource: ShoppingListRemoteDatasource
) : ShoppingListRepository {

    override suspend fun fetchCartItems(): List<CartItem> {
        return remoteDatasource.fetchCartItems();
    }

    override suspend fun updateCart(items: List<CartItem>) {
        return remoteDatasource.updateCart(items);
    }

    override suspend fun clearCart() {
        return remoteDatasource.clearCart();
    }

    override suspend fun deleteItem(itemId: String) {
        return remoteDatasource.deleteItem(itemId)
    }

}
