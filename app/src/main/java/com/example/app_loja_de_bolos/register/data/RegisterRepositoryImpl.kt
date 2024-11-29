package com.example.app_loja_de_bolos.register.data

import com.example.app_loja_de_bolos.register.data.remote.RegisterRemoteDatasource

class RegisterRepositoryImpl(
    private val remoteDatasource: RegisterRemoteDatasource
) : RegisterRepository {
    override suspend fun createAccount(name: String, email: String, password: String) {
        return remoteDatasource.createAccount(name, email, password)
    }
}