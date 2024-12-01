package com.example.app_loja_de_bolos.cake_details.data

import com.example.app_loja_de_bolos.cake_details.model.CakeDetails

interface CakeDetailsRepository {
    suspend fun fetchCakeDetails(cakeId: String, cakeType: String, cakeCategory: String): CakeDetails?
    suspend fun addItemToCart(cake: CakeDetails): Boolean?
}