package com.example.app_loja_de_bolos.cake_list.di

import com.example.app_loja_de_bolos.cake_list.data.CakeListRepository
import com.example.app_loja_de_bolos.cake_list.data.CakeListRepositoryImpl
import com.example.app_loja_de_bolos.cake_list.data.remote.CakeListRemoteDatasource
import com.example.app_loja_de_bolos.cake_list.data.remote.CakeListRemoteDatasourceImpl
import com.example.app_loja_de_bolos.cake_list.presentation.CakeListViewModel
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val cakeListModule = module {
    factory<CakeListRemoteDatasource> {
        CakeListRemoteDatasourceImpl(firebaseFirestore = FirebaseFirestore.getInstance())
    }

    factory<CakeListRepository> {
        CakeListRepositoryImpl(remoteDatasource = get())
    }

    viewModel {
        CakeListViewModel(cakeListRepository = get())
    }
}