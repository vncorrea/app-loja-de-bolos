package com.example.app_loja_de_bolos.cake_details.data.remote

import com.example.app_loja_de_bolos.cake_details.model.CakeDetails

interface CakeDetailsRemoteDatasource {
    suspend fun fetchCakeDetails(cakeId: String): CakeDetails?
}
