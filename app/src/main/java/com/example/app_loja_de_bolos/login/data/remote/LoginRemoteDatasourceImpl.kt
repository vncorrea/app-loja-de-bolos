package com.example.app_loja_de_bolos.login.data.remote

import com.example.app_loja_de_bolos.login.model.UserLogin
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class LoginRemoteDatasourceImpl(
    private val firebaseAuth: FirebaseAuth
): LoginRemoteDatasource {
    override fun isSessionValid(): Boolean {
        return firebaseAuth.currentUser != null
    }

    override suspend fun signin(email: String, password: String): UserLogin {
        val authResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()
        return mapToUserAuth(authResult)
    }

    override suspend fun recover(email: String) {
        firebaseAuth.sendPasswordResetEmail(email).await()
    }

    private fun mapToUserAuth(authResult: AuthResult): UserLogin {
        authResult.user?.let { user ->
            return UserLogin(
                user.uid,
                user.displayName,
                user.email ?: "",
                (user.photoUrl ?: null).toString(),
                user.phoneNumber,
                user.isEmailVerified
            )
        } ?: throw UserNotFoundException()
    }
}