package com.example.app_loja_de_bolos.cake_list.data

import com.example.app_loja_de_bolos.cake_list.model.Cake

interface CakeListRepository {
    suspend fun getCakesByCategory(cakeType: String?, cakeCategory: String?): List<Cake>
}