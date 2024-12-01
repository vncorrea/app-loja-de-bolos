package com.example.app_loja_de_bolos.cake_list.data.remote

import android.util.Log
import com.example.app_loja_de_bolos.cake_list.model.Cake
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await

class CakeListRemoteDatasourceImpl(
    private val firebaseFirestore: FirebaseFirestore
) : CakeListRemoteDatasource {

    override suspend fun getCakesByCategory(cakeType: String?, cakeCategory: String?): List<Cake> {
        return try {
            if (cakeType == null || cakeCategory == null) {
                return emptyList()
            }

            val documentReference =
                firebaseFirestore.collection("bolos").document(cakeType).collection(cakeCategory)

            val querySnapshot = documentReference.get().await()

            if (querySnapshot.isEmpty) {
                return emptyList();
            }

            return mapToCake(querySnapshot)
        } catch (e: Exception) {
            Log.e("CakeList", "Erro ao buscar bolos: ${e.message}", e)
            emptyList()
        }
    }

    private suspend fun mapToCake(querySnapshot: com.google.firebase.firestore.QuerySnapshot): List<Cake> {
        return querySnapshot.documents.map { document ->
            Cake(
                name = document.get("name") as? String ?: "",
                description = document.get("description") as? String ?: "",
                value = formatToUserValue(document.get("value") as? String ?: "R$ 0,00"),
                formattedName = formatToUserCakeCategoryName(document.get("name") as? String ?: ""),
                imageUrl = getImageUrl(document.id)
            )
        }
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

    private fun formatToUserValue(value: String): String {
        return "R$ " + value.replace(".", ",").replace(",", ",0")
    }

    private fun formatToUserCakeCategoryName(cakeName: String): String {
        return cakeName.replace("_", " ").capitalize();
    }
}