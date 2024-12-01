package com.example.app_loja_de_bolos.shoplist.data.remote;

import com.example.app_loja_de_bolos.shoplist.model.CartItem

import java.util.List;

class ShoplistRemoteDatasourceImpl : <fun> ShoplistRemoteDatasource {

    override suspend fun fetchCartItems():List<CartItem> {
        // Lógica para buscar itens do carrinho do backend
        return emptyList() // Exemplo
    }

    override suspend fun updateCart(items: List<CartItem>) {
        // Lógica para atualizar o carrinho no backend
    }
}
