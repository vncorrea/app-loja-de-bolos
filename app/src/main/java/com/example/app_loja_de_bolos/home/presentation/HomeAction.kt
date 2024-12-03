package com.example.app_loja_de_bolos.home.presentation

sealed class HomeAction {
    object ShowErrorMsg : HomeAction()
    data class NavigateCakesList(val type: String) : HomeAction()
    class NavigateShoppingList() : HomeAction()
    class NavigateLogin() : HomeAction()
}