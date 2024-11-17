package com.example.mvvmapplication.login.data.remote

import com.example.app_loja_de_bolos.login.model.UserAuth

interface LoginRemoteDatasource {
    suspend fun createAccount(email: String, password: String): UserAuth
    suspend fun signin(email: String, password: String): UserAuth
    suspend fun recover(email: String)
    fun isSessionValid(): Boolean
}