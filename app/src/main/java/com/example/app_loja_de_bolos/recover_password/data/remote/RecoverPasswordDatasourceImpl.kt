package com.example.app_loja_de_bolos.recover_password.data.remote

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class RecoverPasswordDatasourceImpl(
    private val firebaseAuth: FirebaseAuth
) : RecoverPasswordDatasource {
    override suspend fun recover(email: String) {
        firebaseAuth.sendPasswordResetEmail(email).await()
    }
}