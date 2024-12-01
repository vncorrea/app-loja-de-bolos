package com.example.app_loja_de_bolos.cake_details.presentation

sealed class CakeDetailsAction {
    object NAVIGATE_CAKE_EXTRA : CakeDetailsAction()
    object SHOW_ERROR_MSG : CakeDetailsAction()
}