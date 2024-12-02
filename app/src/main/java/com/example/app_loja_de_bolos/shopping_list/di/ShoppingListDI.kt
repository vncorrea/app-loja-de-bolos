package com.example.app_loja_de_bolos.shopping_list.di

import com.example.app_loja_de_bolos.shopping_list.data.ShoppingListRepository
import com.example.app_loja_de_bolos.shopping_list.data.ShoppingListRepositoryImpl
import com.example.app_loja_de_bolos.shopping_list.data.remote.ShoppingListRemoteDatasource
import com.example.app_loja_de_bolos.shopping_list.data.remote.ShoppingListRemoteDatasourceImpl
import com.example.app_loja_de_bolos.shopping_list.presentation.ShoppingListViewModel
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val shoppingListModule = module {
    factory<ShoppingListRemoteDatasource> {
        ShoppingListRemoteDatasourceImpl(firebaseFirestore = FirebaseFirestore.getInstance())
    }

    factory<ShoppingListRepository> {
        ShoppingListRepositoryImpl(remoteDatasource = get())
    }

    viewModel {
        ShoppingListViewModel(shoppingListRepository = get())
    }
}