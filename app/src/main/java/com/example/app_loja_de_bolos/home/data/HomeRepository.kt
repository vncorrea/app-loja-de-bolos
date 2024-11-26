package com.example.app_loja_de_bolos.home.data

import com.google.firebase.firestore.CollectionReference


interface HomeRepository {
    fun getCakeTypes(): CollectionReference
}