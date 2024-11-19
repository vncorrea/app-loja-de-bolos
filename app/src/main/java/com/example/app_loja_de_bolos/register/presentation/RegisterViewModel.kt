package com.example.app_loja_de_bolos.register.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_loja_de_bolos.login.data.LoginRepository
import com.example.app_loja_de_bolos.register.data.RegisterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val registerRepository: RegisterRepository
) : ViewModel() {

    private val _uiAction = MutableSharedFlow<RegisterAction>()
    val uiAction: SharedFlow<RegisterAction> = _uiAction.asSharedFlow()

    fun onCreateAccountClicked(nameText: String, emailText: String, passwordText: String) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                registerRepository.createAccount(nameText, emailText, passwordText)
                _uiAction.emit(RegisterAction.NAVIGATE_LOGIN)
            }.onFailure { e ->
                _uiAction.emit(RegisterAction.SHOW_ERROR_MSG)
                Log.e("LOGIN", e.message ?: "unknown", e)
            }
        }
    }
}