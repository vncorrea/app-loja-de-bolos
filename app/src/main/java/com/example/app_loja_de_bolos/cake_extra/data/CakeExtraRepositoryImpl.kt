package com.example.app_loja_de_bolos.cake_extra.data

import com.example.app_loja_de_bolos.cake_extra.data.remote.CakeExtraRemoteDataSource
import com.example.app_loja_de_bolos.cake_extra.model.CakeExtra // Altere para o seu modelo específico
import com.example.app_loja_de_bolos.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CakeExtraRepositoryImpl(
    private val remoteDataSource: CakeExtraRemoteDataSource // Injeção do remote data source
) : CakeExtraRepository {

    override suspend fun getCakeExtras(cakeId: String): Result<List<CakeExtra>> {
        return withContext(Dispatchers.IO) {
            try {
                // Fazendo a chamada para o remote data source (API)
                val response = remoteDataSource.fetchCakeExtras(cakeId)

                // Verifica se a resposta é válida e retorna o resultado
                if (response.isSuccessful) {
                    val cakeExtras = response.body() ?: emptyList()
                    Result.Success(cakeExtras) // Resultado de sucesso com os dados
                } else {
                    Result.Error(Exception("Erro ao carregar extras do bolo"))
                }
            } catch (e: Exception) {
                // Caso ocorra uma exceção
                Result.Error(e)
            }
        }
    }
}
