package com.example.app_loja_de_bolos.cake_details.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_loja_de_bolos.cake_details.data.CakeDetailsRepository
import com.example.app_loja_de_bolos.cake_details.model.CakeDetails
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CakeDetailsViewModel(
    private val cakeDetailsRepository: CakeDetailsRepository
) : ViewModel() {

    private val _cakeDetails = MutableStateFlow<CakeDetails?>(null)
    val cakeDetails: StateFlow<CakeDetails?> = _cakeDetails

    private val _action = MutableStateFlow<CakeDetailsAction?>(null)
    val action: StateFlow<CakeDetailsAction?> = _action

    fun fetchCakeDetails(cakeId: String, cakeType: String, cakeCategory: String) {
        viewModelScope.launch {
            try {
                val cakeDetails = cakeDetailsRepository.fetchCakeDetails(cakeId, cakeType, cakeCategory)
                _cakeDetails.value = cakeDetails
                _action.value = cakeDetails?.let { CakeDetailsAction.ShowDetails(it) }
            } catch (e: Exception) {
                _action.value = CakeDetailsAction.ShowError
            }
        }
    }
}
