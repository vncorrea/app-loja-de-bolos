package com.example.app_loja_de_bolos.cake_extra.di

import com.example.app_loja_de_bolos.cake_extra.data.CakeExtraRepository
import com.example.app_loja_de_bolos.cake_extra.data.CakeExtraRepositoryImpl
import com.example.app_loja_de_bolos.cake_extra.data.remote.CakeExtraRemoteDataSource
import com.example.app_loja_de_bolos.cake_extra.data.remote.CakeExtraRemoteDataSourceImpl
import com.example.app_loja_de_bolos.cake_extra.presentation.CakeExtraViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val cakeExtraModule = module {
    // Fornecendo o remote data source para CakeExtra
    factory<CakeExtraRemoteDataSource> {
        CakeExtraRemoteDataSourceImpl(apiService = get()) // Aqui estou supondo que você tenha um ApiService configurado
    }

    // Fornecendo o repository para CakeExtra
    factory<CakeExtraRepository> {
        CakeExtraRepositoryImpl(remoteDataSource = get())
    }

    // ViewModel para CakeExtra
    viewModel {
        CakeExtraViewModel(cakeExtraRepository = get())
    }
}
