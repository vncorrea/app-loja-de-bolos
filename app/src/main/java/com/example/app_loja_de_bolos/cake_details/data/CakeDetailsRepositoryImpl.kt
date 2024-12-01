package com.example.app_loja_de_bolos.cake_details.data

import com.example.app_loja_de_bolos.cake_details.data.remote.CakeDetailsRemoteDatasource
import com.example.app_loja_de_bolos.cake_details.model.CakeDetails
import com.example.app_loja_de_bolos.cake_list.data.CakeListRepository
import com.example.app_loja_de_bolos.cake_list.data.remote.CakeListRemoteDatasource

class CakeDetailsRepositoryImpl(
    private val remoteDatasource: CakeDetailsRemoteDatasource
) : CakeDetailsRepository {
    override suspend fun fetchCakeDetails(
        cakeId: String,
        cakeType: String,
        cakeCategory: String
    ): CakeDetails? {
        return remoteDatasource.fetchCakeDetails(cakeId, cakeType, cakeCategory)
    }
}
