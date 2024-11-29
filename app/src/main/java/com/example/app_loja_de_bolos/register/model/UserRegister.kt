package com.example.app_loja_de_bolos.register.model

data class UserRegister(
    val id: String? = null,
    val email: String? = null,
    val displayName: String = "",
    val photoUrl: String? = null,
    val phoneNumber: String? = null,
    val isEmailVerified: Boolean = false
)