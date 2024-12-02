package com.example.app_loja_de_bolos.shopping_list.model

data class CartItem(
    val id: String,
    val name: String,
    val value: String,
    val imageUrl: String,
    val quantity: Int
)

