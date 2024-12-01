package com.example.app_loja_de_bolos.shoplist.di

import com.example.app_loja_de_bolos.shoplist.data.ShoplistRepository
import com.example.app_loja_de_bolos.shoplist.data.ShoplistRepositoryImpl
import com.example.app_loja_de_bolos.shoplist.presentation.ShoplistViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val shoplistModule = module {

    // Repository
    factory<ShoplistRepository> { ShoplistRepositoryImpl() }

    // ViewModel
    viewModel { ShoplistViewModel(shoplistRepository = get()) }
}
