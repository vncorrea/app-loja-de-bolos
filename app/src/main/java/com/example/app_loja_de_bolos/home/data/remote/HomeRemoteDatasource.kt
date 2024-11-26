package com.example.app_loja_de_bolos.home.data.remote

import com.google.firebase.firestore.CollectionReference


interface HomeRemoteDatasource {
    fun getCakeTypes(): CollectionReference
    fun getCakeImages()
}