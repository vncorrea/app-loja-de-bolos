package com.example.app_loja_de_bolos.recover_password.data

interface RecoverPasswordRepository {
    suspend fun recover(email: String)
}