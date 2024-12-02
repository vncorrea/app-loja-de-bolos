package com.example.app_loja_de_bolos.shopping_list.data.remote

import android.util.Log
import com.example.app_loja_de_bolos.cake_list.model.Cake
import com.example.app_loja_de_bolos.shopping_list.model.CartItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class ShoppingListRemoteDatasourceImpl(
    private val firebaseFirestore: FirebaseFirestore
) : ShoppingListRemoteDatasource {

    private val TAG = "ShoppingListRemoteDS"

    private fun getUserId(): String? {
        return FirebaseAuth.getInstance().currentUser?.uid
    }

    override suspend fun fetchCartItems(): List<CartItem> {
        val userId = getUserId()
        if (userId == null) {
            Log.e(TAG, "Usuário não autenticado.")
            return emptyList()
        }

        return try {
            val snapshot = firebaseFirestore.collection("cart")
                .document(userId)
                .collection("items")
                .get()
                .await()

            return mapToCartItem(snapshot)
        } catch (e: Exception) {
            Log.e(TAG, "Erro ao buscar itens do carrinho: ${e.message}", e)
            emptyList()
        }
    }

    override suspend fun updateCart(items: List<CartItem>) {
        val userId = getUserId()
        if (userId == null) {
            Log.e(TAG, "Usuário não autenticado.")
            return
        }

        try {
            val userCartRef = firebaseFirestore.collection("cart")
                .document(userId)
                .collection("items")

            for (item in items) {
                userCartRef.document(item.id)
                    .set(item)
                    .await()
            }

            Log.d(TAG, "Carrinho atualizado com sucesso.")
        } catch (e: Exception) {
            Log.e(TAG, "Erro ao atualizar o carrinho: ${e.message}", e)
        }
    }

    override suspend fun clearCart() {
        val userId = getUserId()
        if (userId == null) {
            Log.e(TAG, "Usuário não autenticado.")
            return
        }

        try {
            val userCartRef = firebaseFirestore.collection("cart")
                .document(userId)
                .collection("items")

            val snapshot = userCartRef.get().await()
            for (document in snapshot.documents) {
                userCartRef.document(document.id).delete().await()
            }

            Log.d(TAG, "Carrinho limpo com sucesso.")
        } catch (e: Exception) {
            Log.e(TAG, "Erro ao limpar o carrinho: ${e.message}", e)
        }
    }

    override suspend fun deleteItem(itemId: String) {
        val userId = getUserId()
        if (userId == null) {
            Log.e(TAG, "Usuário não autenticado.")
            return
        }

        try {
            firebaseFirestore.collection("cart")
                .document(userId)
                .collection("items")
                .document(itemId)
                .delete()
                .await()

            Log.d(TAG, "Item $itemId removido com sucesso.")
        } catch (e: Exception) {
            Log.e(TAG, "Erro ao remover o item $itemId: ${e.message}", e)
        }
    }

    private suspend fun mapToCartItem(querySnapshot: com.google.firebase.firestore.QuerySnapshot): List<CartItem> {
        return querySnapshot.documents.map { document ->
            CartItem(
                id = document.id,
                name = document.getString("name") ?: "",
                value = document.getString("value") ?: "0.0",
                quantity = document.getLong("quantity")?.toInt() ?: 1,
                imageUrl = document.getString("imageUrl") ?: ""
            )
        }
    }

    override suspend fun updateItem(cartItem: CartItem) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
            ?: throw Exception("Usuário não autenticado")

        firebaseFirestore.collection("cart")
            .document(userId)
            .collection("items")
            .document(cartItem.id)
            .set(cartItem)
            .await()
    }
}