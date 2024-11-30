package com.example.app_loja_de_bolos.cake_category_list.data.remote

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class CakeCategoryListRemoteDatasourceImpl(
    private val firebaseFirestore: FirebaseFirestore
) : CakeCategoryListRemoteDatasource {

    override suspend fun getCakeCategories(cakeType: String?): List<String> {
        return try {
            if (cakeType == null) {
                return emptyList()
            }

            val documentReference = firebaseFirestore.collection("bolos").document(cakeType)

            val documentSnapshot = documentReference.get().await()

            if (documentSnapshot.exists()) {
                val categorias = documentSnapshot.get("categorias") as? List<String>

                if (categorias == null) {
                    Log.e("CakeCategoryList", "Campo 'categorias' não encontrado ou é nulo")
                    return emptyList()
                }

                return categorias
            } else {
                return emptyList()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}