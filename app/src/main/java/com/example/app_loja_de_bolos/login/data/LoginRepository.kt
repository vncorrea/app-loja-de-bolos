package com.example.app_loja_de_bolos.login.data

import com.example.app_loja_de_bolos.login.model.UserAuth

interface LoginRepository {
    fun isSessionValid(): Boolean
    suspend fun createAccount(email: String, password: String): UserAuth
    suspend fun login(email: String, password: String): UserAuth
    suspend fun recover(email: String)
}