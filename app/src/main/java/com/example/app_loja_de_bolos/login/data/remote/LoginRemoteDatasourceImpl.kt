package com.example.app_loja_de_bolos.login.data.remote

import com.example.app_loja_de_bolos.login.model.UserAuth
import com.example.mvvmapplication.login.data.remote.LoginRemoteDatasource
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class LoginRemoteDatasourceImpl(
    private val firebaseAuth: FirebaseAuth
): LoginRemoteDatasource {
    override fun isSessionValid(): Boolean {
        return firebaseAuth.currentUser != null
    }

    override suspend fun createAccount(email: String, password: String): UserAuth {
        val authResult = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
        return mapToUserAuth(authResult)
    }

    override suspend fun signin(email: String, password: String): UserAuth {
        val authResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()
        return mapToUserAuth(authResult)
    }

    override suspend fun recover(email: String) {
        firebaseAuth.sendPasswordResetEmail(email).await()
    }

    private fun mapToUserAuth(authResult: AuthResult): UserAuth {
        authResult.user?.let { user ->
            return UserAuth(
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