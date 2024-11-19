package com.example.app_loja_de_bolos.login.data

import com.example.app_loja_de_bolos.login.model.UserLogin

interface LoginRepository {
    fun isSessionValid(): Boolean
    suspend fun login(email: String, password: String): UserLogin
}