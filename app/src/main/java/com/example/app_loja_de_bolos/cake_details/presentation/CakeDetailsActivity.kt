package com.example.app_loja_de_bolos.cake_details.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.app_loja_de_bolos.R
import com.example.app_loja_de_bolos.cake_details.model.CakeDetails

class CakeDetailsActivity : AppCompatActivity() {

    private val viewModel: CakeDetailsViewModel by lazy {
        ViewModelProvider(this).get(CakeDetailsViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.product_details)

        val cakeId = intent.getStringExtra("CAKE_NAME") // Recebendo o ID do bolo

        cakeId?.let {
            viewModel.fetchCakeDetails(it)
        }

        viewModel.cakeDetails.observe(this, Observer { action ->
            when (action) {
                is CakeDetailsAction.ShowDetails -> showCakeDetails(action.cakeDetails)
                is CakeDetailsAction.ShowError -> showError()
            }
        })
    }

    private fun showCakeDetails(cakeDetails: CakeDetails) {
        // Exibir detalhes do bolo na UI
    }

    private fun showError() {
        // Exibir erro na UI
    }
}
