package com.example.app_loja_de_bolos.cake_details.data.remote

import com.example.app_loja_de_bolos.cake_details.model.CakeDetails
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class CakeDetailsRemoteDatasourceImpl(
    private val firebaseFirestore: FirebaseFirestore
) : CakeDetailsRemoteDatasource {
    override suspend fun fetchCakeDetails(cakeId: String): CakeDetails? {
        return try {
            val snapshot = firebaseFirestore.collection("cakes")
                .document(cakeId)
                .get()
                .await()

            snapshot.toObject(CakeDetails::class.java)
        } catch (e: Exception) {
            null
        }
    }
}
