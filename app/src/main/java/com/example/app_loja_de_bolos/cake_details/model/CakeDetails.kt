package com.example.app_loja_de_bolos.cake_details.model

class CakeDetails {
    data class CakeDetails(
        val id: String,
        val name: String,
        val description: String,
        val price: Double,
        val imageUrl: String
    )

}