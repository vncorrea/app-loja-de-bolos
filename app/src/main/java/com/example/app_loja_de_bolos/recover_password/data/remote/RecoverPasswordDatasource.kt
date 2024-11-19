package com.example.app_loja_de_bolos.recover_password.data.remote

interface RecoverPasswordDatasource {
    suspend fun recover(email: String)
}