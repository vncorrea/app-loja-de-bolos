package com.example.app_loja_de_bolos.cake_details.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.example.app_loja_de_bolos.cake_details.model.CakeDetails
import com.example.app_loja_de_bolos.databinding.ActivityCakeDetailsBinding
import com.example.app_loja_de_bolos.login.presentation.LoginAction
import kotlinx.coroutines.launch

class CakeDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCakeDetailsBinding
    private val viewModel: CakeDetailsViewModel by viewModel()

    private var basePrice = 0.0
    private var currentPrice = 0.0
    private val extraSyrupPrice = 6.0
    private val extraPackagingPrice = 2.50
    private var totalQuantity = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCakeDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val cakeCategory = intent.getStringExtra("cakeCategory")
        val cakeType = intent.getStringExtra("cakeType")
        val cakeId = intent.getStringExtra("cakeId")

        if (cakeId.isNullOrEmpty() || cakeType.isNullOrEmpty() || cakeCategory.isNullOrEmpty()) {
            Toast.makeText(this, "Erro ao carregar detalhes do bolo.", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        viewModel.fetchCakeDetails(cakeId, cakeType, cakeCategory)

        observeViewModel()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiAction.collect { action ->
                    action?.let {
                        executeAction(it)
                    }
                }
            }
        }

        setupListeners()
    }

    private fun executeAction(action: CakeDetailsAction) {
        when (action) {
            CakeDetailsAction.NAVIGATE_CAKE_EXTRA -> {
                viewModel.cakeDetails.value?.let {  }
            }
            CakeDetailsAction.SHOW_ERROR_MSG -> showError()
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.cakeDetails.collect { cakeDetails ->
                cakeDetails?.let {
                    basePrice = extractPrice(it.value)
                    currentPrice = basePrice
                    showCakeDetails(it)
                }
            }
        }
    }

    private fun setupListeners() {
        with(binding) {
            chocolateSyrupSwitch.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    currentPrice += extraSyrupPrice
                } else {
                    currentPrice -= extraSyrupPrice
                }
                updatePriceDisplay()
            }

            plasticPackagingSwitch.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    currentPrice += extraPackagingPrice
                } else {
                    currentPrice -= extraPackagingPrice
                }
                updatePriceDisplay()
            }

            increaseTotalQuantity.setOnClickListener {
                totalQuantity++
                currentPrice = currentPrice * totalQuantity
                updateQuantityAndPrice()
            }

            decreaseTotalQuantity.setOnClickListener {
                if (totalQuantity > 1) {
                    totalQuantity--
                    currentPrice = currentPrice * totalQuantity
                    updateQuantityAndPrice()
                } else {
                    Toast.makeText(
                        this@CakeDetailsActivity,
                        "A quantidade mínima é 1.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun updateQuantityAndPrice() {
        with(binding) {
            totalQuantityText.text = totalQuantity.toString()
            cakePrice.text = formatToCurrency(currentPrice)
        }
    }

    private fun updatePriceDisplay() {
        binding.cakePrice.text = formatToCurrency(currentPrice)
    }


    private fun showCakeDetails(cakeDetails: CakeDetails) {
        Log.d("CakeDetailsActivity", "showCakeDetails: $cakeDetails")
        with(binding) {
            cakeName.text = cakeDetails.name
            cakeDescription.text = cakeDetails.description
            cakePrice.text = cakeDetails.value

            Glide.with(cakeImage.context)
                .load(cakeDetails.imageUrl)
//                .placeholder(R.drawable.placeholder) // Imagem temporária
//                .error(R.drawable.error_image) // Imagem em caso de erro
                .into(cakeImage)
        }
    }

    private fun extractPrice(value: String): Double {
        return value.replace("R$", "")
            .replace(",", ".")
            .trim()
            .toDouble()
    }

    private fun formatToCurrency(value: Double): String {
        return String.format("R$ %.2f", value).replace(".", ",")
    }

    private fun showError() {
        Toast.makeText(this, "Erro ao carregar detalhes do bolo.", Toast.LENGTH_SHORT).show()
    }
}