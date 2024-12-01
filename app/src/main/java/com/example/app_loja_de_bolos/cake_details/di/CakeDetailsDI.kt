package com.example.app_loja_de_bolos.cake_details.di

import com.example.app_loja_de_bolos.cake_details.data.CakeDetailsRepository
import com.example.app_loja_de_bolos.cake_details.data.CakeDetailsRepositoryImpl
import com.example.app_loja_de_bolos.cake_details.data.remote.CakeDetailsRemoteDatasource
import com.example.app_loja_de_bolos.cake_details.data.remote.CakeDetailsRemoteDatasourceImpl
import com.example.app_loja_de_bolos.cake_details.presentation.CakeDetailsViewModel
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val cakeDetailsModule = module {
    factory<CakeDetailsRemoteDatasource> {
        CakeDetailsRemoteDatasourceImpl(firebaseFirestore = FirebaseFirestore.getInstance())
    }

    factory<CakeDetailsRepository> {
        CakeDetailsRepositoryImpl(remoteDatasource = get())
    }

    viewModel {
        CakeDetailsViewModel(cakeDetailsRepository = get())
    }
}
