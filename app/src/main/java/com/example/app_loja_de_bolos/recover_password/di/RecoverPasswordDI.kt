package com.example.app_loja_de_bolos.recover_password.di

import com.example.app_loja_de_bolos.recover_password.data.RecoverPasswordRepository
import com.example.app_loja_de_bolos.recover_password.data.RecoverPasswordRepositoryImpl
import com.example.app_loja_de_bolos.recover_password.data.remote.RecoverPasswordDatasource
import com.example.app_loja_de_bolos.recover_password.data.remote.RecoverPasswordDatasourceImpl
import com.example.app_loja_de_bolos.recover_password.presentation.RecoverPasswordViewModel
import com.google.firebase.auth.FirebaseAuth
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val recoverPasswordModule = module {
    factory<RecoverPasswordDatasource> {
        RecoverPasswordDatasourceImpl(firebaseAuth = FirebaseAuth.getInstance())
    }

    factory<RecoverPasswordRepository> {
        RecoverPasswordRepositoryImpl(remoteDatasource = get())
    }

    viewModel {
        RecoverPasswordViewModel(recoverPasswordRepository = get())
    }
}