package com.example.app_loja_de_bolos.home.presentation

import androidx.lifecycle.ViewModel
import com.example.app_loja_de_bolos.home.data.HomeRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

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
        _uiAction.tryEmit(HomeAction.NavigateCakesList(type))
    }
}