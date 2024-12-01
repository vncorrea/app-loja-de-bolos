package com.example.app_loja_de_bolos.cake_category_list.data.remote

import android.util.Log
import com.example.app_loja_de_bolos.cake_category_list.model.CakeCategory
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class CakeCategoryListRemoteDatasourceImpl(
    private val firebaseFirestore: FirebaseFirestore
) : CakeCategoryListRemoteDatasource {

    override suspend fun getCakeCategories(cakeType: String?): List<CakeCategory> {
        return try {
            if (cakeType == null) {
                return emptyList()
            }

            val documentReference = firebaseFirestore.collection("bolos").document(cakeType)

            val documentSnapshot = documentReference.get().await()

            if (!documentSnapshot.exists()) {
                return emptyList();
            }

            val categorias = documentSnapshot.get("categorias") as? List<String>

            if (categorias == null) {
                Log.e("CakeCategoryList", "Campo 'categorias' não encontrado ou é nulo")
                return emptyList()
            }

            return mapToCakeCategory(categorias)
        } catch (e: Exception) {
            Log.e("CakeCategoryList", "Erro ao buscar categorias: ${e.message}", e)
            emptyList()
        }
    }

    private fun mapToCakeCategory(categorias: List<String>): List<CakeCategory> {
        return categorias.map { category ->
            CakeCategory(
                id = null,
                name = formatToDatabaseCakeCategoryName(category),
                formattedName = formatToUserCakeCategoryName(category)
            )
        }
    }

    private fun formatToUserCakeCategoryName(category: String): String {
        return category.replace("_", " ").capitalize();
    }

    private fun formatToDatabaseCakeCategoryName(category: String): String {
        return when (category.lowercase()) {
            "bolos_médios" -> "bolo_m"
            "bolos_mini" -> "bolo_mini"
            "bolos_grandes" -> "bolo_g"
            else -> category.replace(" ", "_").lowercase()
        }
    }
}