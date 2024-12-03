package com.example.app_loja_de_bolos.home.data.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class HomeRemoteDatasourceImpl(
    private val firebaseFirestore: FirebaseFirestore
) : HomeRemoteDatasource {
    override fun getCakeTypes(): CollectionReference {
        return firebaseFirestore.collection("bolos")
    }

    override fun logout(): Unit {
        FirebaseAuth.getInstance().signOut()
    }
}