package com.example.app_loja_de_bolos.shoplist.presentation

sealed class ShoplistAction {
    object FetchCartItems : ShoplistAction()
    data class AddItemToCart(val item: CartItem) : ShoplistAction()
    data class RemoveItemFromCart(val itemId: String) : ShoplistAction()
    object ClearCart : ShoplistAction()
}
