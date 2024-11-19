package com.example.app_loja_de_bolos.login.data

import com.example.app_loja_de_bolos.login.data.remote.LoginRemoteDatasource
import com.example.app_loja_de_bolos.login.model.UserLogin

class LoginRepositoryImpl(
    private val remoteDatasource: LoginRemoteDatasource
) : LoginRepository {
    override fun isSessionValid(): Boolean {
        return remoteDatasource.isSessionValid()
    }

    override suspend fun login(email: String, password: String): UserLogin {
        return remoteDatasource.signin(email, password)
    }
}