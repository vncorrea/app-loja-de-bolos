package com.example.app_loja_de_bolos.cake_details.data

import com.example.app_loja_de_bolos.cake_details.data.remote.CakeDetailsRemoteDatasource
import com.example.app_loja_de_bolos.cake_details.model.CakeDetails

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

    override suspend fun addItemToCart(cake: CakeDetails): Boolean? {
        return remoteDatasource.addItemToCart(cake)
    }
}
