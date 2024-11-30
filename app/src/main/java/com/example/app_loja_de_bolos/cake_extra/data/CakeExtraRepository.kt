package com.example.app_loja_de_bolos.cake_extra.data

import com.example.app_loja_de_bolos.cake_extra.data.remote.CakeExtraRemoteDatasource
import com.example.app_loja_de_bolos.domain.model.TamanhoBolo

class ProdutoExtraRepository(
    private val remoteDataSource: ProdutoExtraRemoteDataSource
) {

    suspend fun getTamanhos(): List<TamanhoBolo> {
        return remoteDataSource.getTamanhos()
    }

    suspend fun getCaldaChocolate(): Boolean {
        return remoteDataSource.getCaldaChocolate()
    }

    suspend fun getEmbalagemPlastica(): Boolean {
        return remoteDataSource.getEmbalagemPlastica()
    }
}

class CakeExtraRepository {

}
