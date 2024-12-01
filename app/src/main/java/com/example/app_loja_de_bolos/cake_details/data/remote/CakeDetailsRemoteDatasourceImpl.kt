package com.example.app_loja_de_bolos.cake_details.data.remote

import android.util.Log
import com.example.app_loja_de_bolos.cake_details.model.CakeDetails
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await

class CakeDetailsRemoteDatasourceImpl(
    private val firebaseFirestore: FirebaseFirestore
) : CakeDetailsRemoteDatasource {
    override suspend fun fetchCakeDetails(
        cakeId: String,
        cakeType: String,
        cakeCategory: String
    ): CakeDetails? {
        return try {
            Log.d("CakeDetailsRemoteDatasourceImpl", "fetchCakeDetails: $cakeId, $cakeType, $cakeCategory")
            val snapshot = firebaseFirestore.collection("bolos")
                .document(cakeType)
                .collection(cakeCategory)
                .document(cakeId)
                .get()
                .await()

            Log.d("CakeDetailsRemoteDatasourceImpl", "fetchCakeDetails: $snapshot")

            return mapToCakeDetails(snapshot)
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun addItemToCart(item: CakeDetails): Boolean {
        val userId = FirebaseAuth.getInstance().currentUser?.uid

        if (userId == null) {
            Log.e("CakeExtraRemoteDatasourceImpl", "Usuário não está logado.")
            return false
        }

        return try {
            Log.d("CakeExtraRemoteDatasourceImpl", "addItemToCart: $item para o usuário $userId")

            firebaseFirestore.collection("cart")
                .document(userId)
                .collection("items")
                .document(item.id)
                .set(item)
                .await()

            true
        } catch (e: Exception) {
            Log.e(
                "CakeExtraRemoteDatasourceImpl",
                "Erro ao adicionar item ao carrinho: ${e.message}",
                e
            )
            false
        }
    }

    private suspend fun mapToCakeDetails(snapshot: DocumentSnapshot): CakeDetails {
        return CakeDetails(
            id = snapshot.id,
            name = snapshot.getString("name") ?: "",
            description = snapshot.getString("description") ?: "",
            value = formatToUserValue(snapshot.getString("value") ?: ""),
            imageUrl = getImageUrl(snapshot.id)
        )
    }

    private fun formatToUserValue(value: String): String {
        return "R$ " + value.replace(".", ",").replace(",", ",0")
    }

    private suspend fun getImageUrl(cakeName: String): String {
        val storageReference = FirebaseStorage.getInstance().reference
        val imageReference = storageReference.child("$cakeName.png")

        return try {
            imageReference.downloadUrl.await().toString()
        } catch (e: Exception) {
            Log.e("CakeList", "Erro ao buscar URL da imagem para $cakeName: ${e.message}", e)
            ""
        }
    }
}
