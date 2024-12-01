package com.example.app_loja_de_bolos.cake_extra.presentation

data class CakeExtraState(
    val selectedSize: String = "",
    val hasChocolateSyrup: Boolean = false,
    val hasPlasticPackaging: Boolean = false,
    val observation: String = "",
    val isAddedToCart: Boolean = false
)
