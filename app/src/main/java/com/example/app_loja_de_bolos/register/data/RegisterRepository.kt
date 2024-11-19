package com.example.app_loja_de_bolos.register.data

interface RegisterRepository {
    suspend fun createAccount(name: String, email: String, password: String)
}