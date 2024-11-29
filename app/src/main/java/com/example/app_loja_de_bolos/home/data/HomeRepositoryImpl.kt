package com.example.app_loja_de_bolos.home.data

import com.example.app_loja_de_bolos.home.data.remote.HomeRemoteDatasource
import com.google.firebase.firestore.CollectionReference

class HomeRepositoryImpl(
    private val remoteDatasource: HomeRemoteDatasource
) : HomeRepository {
    override fun getCakeTypes(): CollectionReference = remoteDatasource.getCakeTypes()
}