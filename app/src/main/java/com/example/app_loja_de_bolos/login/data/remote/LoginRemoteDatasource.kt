package com.example.app_loja_de_bolos.login.data.remote

import com.example.app_loja_de_bolos.login.model.UserLogin

interface LoginRemoteDatasource {
    suspend fun signin(email: String, password: String): UserLogin
    fun isSessionValid(): Boolean
}