package com.example.app_loja_de_bolos.cake_details.data

import com.example.app_loja_de_bolos.cake_details.model.CakeDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CakeDetailsRepositoryImpl(
    private val remoteDatasource: CakeDetailsRemoteDatasource
) : CakeDetailsRepository {
    override suspend fun getCakeDetailsById(cakeId: String): Flow<CakeDetails?> = flow {
        emit(remoteDatasource.fetchCakeDetails(cakeId))
    }
}
