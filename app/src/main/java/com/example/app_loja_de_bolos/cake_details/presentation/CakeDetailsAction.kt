package com.example.app_loja_de_bolos.cake_details.presentation

import com.example.app_loja_de_bolos.cake_details.model.CakeDetails

sealed class CakeDetailsAction {
    data class ShowDetails(val cakeDetails: CakeDetails) : CakeDetailsAction()
    object ShowError : CakeDetailsAction()
}
