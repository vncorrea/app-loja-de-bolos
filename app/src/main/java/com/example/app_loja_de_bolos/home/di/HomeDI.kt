package com.example.app_loja_de_bolos.home.di

import com.example.app_loja_de_bolos.home.data.HomeRepository
import com.example.app_loja_de_bolos.home.data.HomeRepositoryImpl
import com.example.app_loja_de_bolos.home.data.remote.HomeRemoteDatasource
import com.example.app_loja_de_bolos.home.data.remote.HomeRemoteDatasourceImpl
import com.example.app_loja_de_bolos.home.presentation.HomeViewModel
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {
    factory<HomeRemoteDatasource> {
        HomeRemoteDatasourceImpl(firebaseFirestore = FirebaseFirestore.getInstance())
    }

    factory<HomeRepository> {
        HomeRepositoryImpl(remoteDatasource = get())
    }

    viewModel {
        HomeViewModel(homeRepository = get())
    }
}