package com.example.app_loja_de_bolos.cake_details.presentation

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.app_loja_de_bolos.cake_details.model.CakeDetails
import com.example.app_loja_de_bolos.databinding.ActivityCakeDetailsBinding
import kotlinx.coroutines.launch

class CakeDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCakeDetailsBinding
    private val viewModel: CakeDetailsViewModel by viewModel()

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
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.cakeDetails.collect { cakeDetails ->
                cakeDetails?.let {
                    showCakeDetails(it)
                }
            }
        }
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

    private fun showError() {
        Toast.makeText(this, "Erro ao carregar detalhes do bolo.", Toast.LENGTH_SHORT).show()
    }
}