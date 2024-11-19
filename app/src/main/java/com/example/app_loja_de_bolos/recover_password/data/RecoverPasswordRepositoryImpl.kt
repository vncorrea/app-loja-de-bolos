package com.example.app_loja_de_bolos.recover_password.data

import com.example.app_loja_de_bolos.recover_password.data.remote.RecoverPasswordDatasource

class RecoverPasswordRepositoryImpl(
    private val remoteDatasource: RecoverPasswordDatasource
) : RecoverPasswordRepository {
    override suspend fun recover(email: String) {
        remoteDatasource.recover(email)
    }
}