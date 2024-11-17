package com.example.app_loja_de_bolos.login.di

import com.example.app_loja_de_bolos.login.data.LoginRepository
import com.example.app_loja_de_bolos.login.data.LoginRepositoryImpl
import com.example.app_loja_de_bolos.login.data.remote.LoginRemoteDatasourceImpl
import com.example.app_loja_de_bolos.login.presentation.LoginViewModel
import com.example.mvvmapplication.login.data.remote.LoginRemoteDatasource
import com.google.firebase.auth.FirebaseAuth
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val loginModule = module {
    factory<LoginRemoteDatasource> {
        LoginRemoteDatasourceImpl(firebaseAuth = FirebaseAuth.getInstance())
    }

    factory<LoginRepository> {
        LoginRepositoryImpl(remoteDatasource = get())
    }

    viewModel {
        LoginViewModel(loginRepository = get())
    }
}