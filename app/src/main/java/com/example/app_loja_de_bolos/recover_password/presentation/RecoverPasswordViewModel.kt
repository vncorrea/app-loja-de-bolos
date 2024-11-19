package com.example.app_loja_de_bolos.recover_password.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_loja_de_bolos.recover_password.data.RecoverPasswordRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class RecoverPasswordViewModel(
    private val recoverPasswordRepository: RecoverPasswordRepository
) : ViewModel() {

    private val _uiAction = MutableSharedFlow<RecoverPasswordAction>()
    val uiAction: SharedFlow<RecoverPasswordAction> = _uiAction.asSharedFlow()

    fun onRecoverPasswordClicked(emailText: String) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                recoverPasswordRepository.recover(emailText)
                _uiAction.emit(RecoverPasswordAction.NAVIGATE_LOGIN)
            }.onFailure { e ->
                _uiAction.emit(RecoverPasswordAction.SHOW_ERROR_MSG)
                Log.e("RECOVER_PASSWORD", e.message ?: "unknown", e)
            }
        }
    }

    fun onBackToLoginClicked() {
        viewModelScope.launch {
            _uiAction.emit(RecoverPasswordAction.NAVIGATE_LOGIN)
        }
    }
}