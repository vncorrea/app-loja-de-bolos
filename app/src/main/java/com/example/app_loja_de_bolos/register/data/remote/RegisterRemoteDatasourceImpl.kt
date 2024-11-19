package com.example.app_loja_de_bolos.register.data.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.tasks.await

class RegisterRemoteDatasourceImpl(
    private val firebaseAuth: FirebaseAuth
) : RegisterRemoteDatasource {
    override suspend fun createAccount(name: String, email: String, password: String) {
        val authResult = firebaseAuth.createUserWithEmailAndPassword(email, password).await()

        authResult.user?.updateProfile(
            UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build()
        )?.await()
    }
}