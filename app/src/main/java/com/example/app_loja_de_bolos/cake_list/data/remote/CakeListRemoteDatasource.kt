package com.example.app_loja_de_bolos.cake_list.data.remote

import com.example.app_loja_de_bolos.cake_list.model.Cake


interface CakeListRemoteDatasource {
    suspend fun getCakesByCategory(cakeType: String?, cakeCategory: String?): List<Cake>
}