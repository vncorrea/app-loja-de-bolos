package com.example.app_loja_de_bolos.cake_extra.data.remote

import com.example.app_loja_de_bolos.cake_details.di.CakeDetailsDI

class ProdutoExtraRemoteDataSourceImpl(
    private val apiService: CakeDetailsDI.ApiService // Supondo que você tenha um ApiService configurado para consumir uma API
) : ProdutoExtraRemoteDataSource {

    override suspend fun getTamanhos(): List<TamanhoBolo> {
        // Aqui você vai fazer a chamada à API para obter os tamanhos dos bolos
        return apiService.getTamanhos() // Supondo que seu ApiService tenha esse método
    }

    class TamanhoBolo {

    }

    override suspend fun getCaldaChocolate(): Boolean {
        // Implementação para buscar a disponibilidade de calda de chocolate
        return apiService.getCaldaChocolate()
    }

    override suspend fun getEmbalagemPlastica(): Boolean {
        // Implementação para verificar se a embalagem plástica está disponível
        return apiService.getEmbalagemPlastica()
    }
}
