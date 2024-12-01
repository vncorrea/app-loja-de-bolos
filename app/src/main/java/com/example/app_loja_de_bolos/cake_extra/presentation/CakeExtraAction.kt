package com.example.app_loja_de_bolos.cake_extra.presentation

sealed class CakeExtraAction {
    data class SelectSize(val size: String) : CakeExtraAction()
    data class ToggleChocolateSyrup(val isEnabled: Boolean) : CakeExtraAction()
    data class TogglePlasticPackaging(val isEnabled: Boolean) : CakeExtraAction()
    data class UpdateObservation(val text: String) : CakeExtraAction()
    object AddToCart : CakeExtraAction()
}
