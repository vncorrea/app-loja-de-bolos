package com.example.app_loja_de_bolos.cake_extra.data

import com.example.app_loja_de_bolos.cake_extra.data.remote.CakeExtraRemoteDatasource
import com.example.app_loja_de_bolos.cake_extra.data.remote.ProdutoExtraRemoteDataSourceImpl
import com.example.app_loja_de_bolos.domain.model.TamanhoBolo

class ProdutoExtraRepository<ProdutoExtraRemoteDataSource>(
    private val remoteDataSource: ProdutoExtraRemoteDataSource
) {

    suspend fun getSize(): List<ProdutoExtraRemoteDataSourceImpl.TamanhoBolo> {
        return remoteDataSource.getSize()
    }

    suspend fun getCaldaChocolate(): Boolean {
        return remoteDataSource.getCaldaChocolate()
    }

    suspend fun getEmbalagemPlastica(): Boolean {
        return remoteDataSource.getEmbalagemPlastica()
    }
}

class CakeExtraRepository {
    fun addToCart(size: String, hasChocolateSyrup: Boolean, hasPlasticPackaging: Boolean, observation: String) {

    }

}
