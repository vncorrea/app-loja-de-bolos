package com.example.app_loja_de_bolos.cake_extra.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_loja_de_bolos.cake_extra.data.CakeExtraRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CakeExtraViewModel(
    private val repository: CakeExtraRepository
) : ViewModel() {

    private val _state = MutableStateFlow(CakeExtraState())
    val state: StateFlow<CakeExtraState> get() = _state

    fun handleAction(action: CakeExtraAction) {
        when (action) {
            is CakeExtraAction.SelectSize -> {
                _state.value = _state.value.copy(selectedSize = action.size)
            }
            is CakeExtraAction.ToggleChocolateSyrup -> {
                _state.value = _state.value.copy(hasChocolateSyrup = action.isEnabled)
            }
            is CakeExtraAction.TogglePlasticPackaging -> {
                _state.value = _state.value.copy(hasPlasticPackaging = action.isEnabled)
            }
            is CakeExtraAction.UpdateObservation -> {
                _state.value = _state.value.copy(observation = action.text)
            }
            CakeExtraAction.AddToCart -> {
                viewModelScope.launch {
                    val currentState = _state.value
                    repository.addToCart(
                        size = currentState.selectedSize,
                        hasChocolateSyrup = currentState.hasChocolateSyrup,
                        hasPlasticPackaging = currentState.hasPlasticPackaging,
                        observation = currentState.observation
                    )
                    _state.value = _state.value.copy(isAddedToCart = true)
                }
            }
        }
    }
}
