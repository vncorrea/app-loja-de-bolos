package com.example.app_loja_de_bolos.login.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_loja_de_bolos.login.data.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginRepository: LoginRepository
) : ViewModel() {

    private val _uiAction = MutableSharedFlow<LoginAction>()
    val uiAction: SharedFlow<LoginAction> = _uiAction.asSharedFlow()

    fun onViewCreated() {
        viewModelScope.launch {
            if (loginRepository.isSessionValid()) {
                _uiAction.emit(LoginAction.NAVIGATE_HOME)
            }
        }
    }

    fun onLoginClicked() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                _uiAction.emit(LoginAction.NAVIGATE_HOME)
            }.onFailure { e ->
                _uiAction.emit(LoginAction.SHOW_ERROR_MSG)
                Log.e("LOGIN", e.message ?: "unknown", e)
            }
        }
    }

    fun onCreateAccountClicked() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                _uiAction.emit(LoginAction.NAVIGATE_REGISTER)
            }.onFailure { e ->
                _uiAction.emit(LoginAction.SHOW_ERROR_MSG)
                Log.e("REGISTER", e.message ?: "unknown", e)
            }
        }
    }
}