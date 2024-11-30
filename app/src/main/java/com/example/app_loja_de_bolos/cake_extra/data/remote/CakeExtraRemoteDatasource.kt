package com.example.app_loja_de_bolos.cake_extra.data.remote

interface ProdutoExtraRemoteDataSource {
    suspend fun getTamanhos(): List<ProdutoExtraRemoteDataSourceImpl.TamanhoBolo>
    suspend fun getCaldaChocolate(): Boolean
    suspend fun getEmbalagemPlastica(): Boolean
}

class CakeExtraRemoteDatasource {

}

class CakeExtraRemoteDataSource {
    fun fetchCakeExtras(cakeId: String): Any {

    }

}
