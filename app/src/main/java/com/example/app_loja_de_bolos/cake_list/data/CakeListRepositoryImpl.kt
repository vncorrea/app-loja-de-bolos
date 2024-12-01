package com.example.app_loja_de_bolos.cake_list.data

import com.example.app_loja_de_bolos.cake_list.data.remote.CakeListRemoteDatasource
import com.example.app_loja_de_bolos.cake_list.model.Cake

class CakeListRepositoryImpl(
    private val remoteDatasource: CakeListRemoteDatasource
) : CakeListRepository {
    override suspend fun getCakesByCategory(cakeType: String?, cakeCategory: String?): List<Cake> {
        return remoteDatasource.getCakesByCategory(cakeType, cakeCategory);
    }
}