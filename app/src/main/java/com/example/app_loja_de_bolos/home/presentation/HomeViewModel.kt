package com.example.app_loja_de_bolos.home.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_loja_de_bolos.home.data.HomeRepository
import com.example.app_loja_de_bolos.login.presentation.LoginAction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val homeRepository: HomeRepository
) : ViewModel() {

    private val _uiAction = MutableSharedFlow<HomeAction>()
    val uiAction: SharedFlow<HomeAction> = _uiAction.asSharedFlow()

    fun onViewCreated() {
//        viewModelScope.launch {
//            if (loginRepository.isSessionValid()) {
//                _uiAction.emit(LoginAction.NAVIGATE_HOME)
//            }
//        }
    }

    fun onCakesListClicked(type: String) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                _uiAction.emit(HomeAction.NavigateCakesList(type))
            }.onFailure { e ->
                Log.e("CAKE LIST CLICK", e.message ?: "unknown", e)
            }
        }
    }

    fun onCartClicked() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                _uiAction.emit(HomeAction.NavigateShoppingList())
            }.onFailure { e ->
                Log.e("CART CLICK", e.message ?: "unknown", e)
            }
        }
    }

    fun onLogoutClicked() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                homeRepository.logout()
                _uiAction.emit(HomeAction.NavigateLogin())
            }.onFailure { e ->
                Log.e("LOGOUT CLICK", e.message ?: "unknown", e)
            }
        }
    }
}