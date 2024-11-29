package com.example.app_loja_de_bolos.register.data.remote


interface RegisterRemoteDatasource {
    suspend fun createAccount(name: String, wemail: String, password: String);
}