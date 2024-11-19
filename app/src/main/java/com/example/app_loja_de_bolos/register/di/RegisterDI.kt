package com.example.app_loja_de_bolos.register.di

import com.example.app_loja_de_bolos.register.data.RegisterRepository
import com.example.app_loja_de_bolos.register.data.RegisterRepositoryImpl
import com.example.app_loja_de_bolos.register.data.remote.RegisterRemoteDatasource
import com.example.app_loja_de_bolos.register.data.remote.RegisterRemoteDatasourceImpl
import com.example.app_loja_de_bolos.register.presentation.RegisterViewModel
import com.google.firebase.auth.FirebaseAuth
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val registerModule = module {
    factory<RegisterRemoteDatasource> {
        RegisterRemoteDatasourceImpl(firebaseAuth = FirebaseAuth.getInstance())
    }

    factory<RegisterRepository> {
        RegisterRepositoryImpl(remoteDatasource = get())
    }

    viewModel {
        RegisterViewModel(registerRepository = get())
    }
}