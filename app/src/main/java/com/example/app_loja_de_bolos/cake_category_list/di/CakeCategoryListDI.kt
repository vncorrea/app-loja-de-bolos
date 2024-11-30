package com.example.app_loja_de_bolos.cake_category_list.di

import com.example.app_loja_de_bolos.cake_category_list.data.CakeCategoryListRepository
import com.example.app_loja_de_bolos.cake_category_list.data.CakeCategoryListRepositoryImpl
import com.example.app_loja_de_bolos.cake_category_list.data.remote.CakeCategoryListRemoteDatasource
import com.example.app_loja_de_bolos.cake_category_list.data.remote.CakeCategoryListRemoteDatasourceImpl
import com.example.app_loja_de_bolos.cake_category_list.presentation.CakeCategoryListViewModel
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val cakeCategoryListModule = module {
    factory<CakeCategoryListRemoteDatasource> {
        CakeCategoryListRemoteDatasourceImpl(firebaseFirestore = FirebaseFirestore.getInstance())
    }

    factory<CakeCategoryListRepository> {
        CakeCategoryListRepositoryImpl(remoteDatasource = get())
    }

    viewModel {
        CakeCategoryListViewModel(cakeCategoryListRepository = get())
    }
}