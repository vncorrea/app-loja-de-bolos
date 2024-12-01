package com.example.app_loja_de_bolos.shoplist.data.remote

interface ShoplistRemoteDatasource {
    suspend fun fetchCartItems(): List<CartItem>
    suspend fun updateCart(items: List<CartItem>)
}
