package com.example.app_loja_de_bolos.cake_details.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_loja_de_bolos.cake_details.di.CakeDetailsDI
import kotlinx.coroutines.launch

class CakeDetailsViewModel(private val repository: CakeDetailsDI.CakeDetailsRepository<Any?>) : ViewModel() {

    private val _cakeDetails = MutableLiveData<CakeDetailsAction>()
    val cakeDetails: LiveData<CakeDetailsAction> get() = _cakeDetails

    fun fetchCakeDetails(cakeId: String) {
        viewModelScope.launch {
            try {
                val cakeDetails = repository.fetchCakeDetails(cakeId)
                _cakeDetails.value = CakeDetailsAction.ShowDetails(cakeDetails)
            } catch (e: Exception) {
                _cakeDetails.value = CakeDetailsAction.ShowError
            }
        }
    }
}
