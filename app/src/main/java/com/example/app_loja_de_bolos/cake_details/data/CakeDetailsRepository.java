package com.example.app_loja_de_bolos.cake_details.data;

import com.example.app_loja_de_bolos.cake_details.model.CakeDetails
import kotlinx.coroutines.flow.Flow

interface CakeDetailsRepository {
    suspend fun getCakeDetailsById(cakeName: String): Flow<CakeDetails?>
}
