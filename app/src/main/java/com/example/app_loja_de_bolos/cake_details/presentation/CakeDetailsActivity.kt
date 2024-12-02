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
import com.example.app_loja_de_bolos.shopping_list.presentation.ShoppingListActivity
import kotlinx.coroutines.launch

class CakeDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCakeDetailsBinding
    private val viewModel: CakeDetailsViewModel by viewModel()

    private var basePrice = 0.0
    private var currentPrice = 0.0
    private var totalQuantity = 1
    private var extraSyrupPrice = 6.0
    private var extraPackagingPrice = 2.50
    private var hasSyrup = false
    private var hasPackaging = false

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

        setupListeners()
    }

    private fun setupListeners() {
        with(binding) {
            chocolateSyrupSwitch.setOnCheckedChangeListener { _, isChecked ->
                hasSyrup = isChecked
                updateTotalPrice()
            }

            plasticPackagingSwitch.setOnCheckedChangeListener { _, isChecked ->
                hasPackaging = isChecked
                updateTotalPrice()
            }

            increaseTotalQuantity.setOnClickListener {
                totalQuantity++
                updateTotalPrice()
            }

            decreaseTotalQuantity.setOnClickListener {
                if (totalQuantity > 1) {
                    totalQuantity--
                    updateTotalPrice()
                } else {
                    Toast.makeText(
                        this@CakeDetailsActivity,
                        "A quantidade mínima é 1.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            addToCartButton.setOnClickListener {
                viewModel.cakeDetails.value?.let { cakeDetails ->
                    val updatedCakeDetails = cakeDetails.copy(
                        quantity = totalQuantity,
                        value = getFormattedCurrentPrice()
                    )
                    viewModel.onAddToCartButtonClicked(updatedCakeDetails)
                    finish()
                } ?: run {
                    Toast.makeText(
                        this@CakeDetailsActivity,
                        "Erro ao adicionar ao carrinho.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    fun getFormattedCurrentPrice(): String {
        return String.format("%.2f", currentPrice).replace(",", ".")
    }

    private fun updateTotalPrice() {
        val extras = calculateExtras()
        currentPrice = (basePrice + extras) * totalQuantity
        updateQuantityAndPrice()
    }

    private fun calculateExtras(): Double {
        var extras = 0.0
        if (hasSyrup) extras += extraSyrupPrice
        if (hasPackaging) extras += extraPackagingPrice
        return extras
    }

    private fun updateQuantityAndPrice() {
        with(binding) {
            totalQuantityText.text = totalQuantity.toString()
            cakePrice.text = formatToCurrency(currentPrice)
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

    private fun showCakeDetails(cakeDetails: CakeDetails) {
        with(binding) {
            cakeName.text = cakeDetails.name
            cakeDescription.text = cakeDetails.description
            cakePrice.text = cakeDetails.value

            Glide.with(cakeImage.context)
                .load(cakeDetails.imageUrl)
                .into(cakeImage)
        }
    }

    private fun showError() {
        Toast.makeText(this, "Erro ao carregar detalhes do bolo.", Toast.LENGTH_SHORT).show()
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
}