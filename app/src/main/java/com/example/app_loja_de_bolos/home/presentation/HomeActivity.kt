package com.example.app_loja_de_bolos.home.presentation

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.app_loja_de_bolos.R
import com.example.app_loja_de_bolos.databinding.ActivityHomeBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val viewModel: HomeViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiAction.collect { action ->
                    executeAction(action)
                }
            }
        }

        viewModel.onViewCreated()

        with(binding) {
            btnBolos.setOnClickListener {
                viewModel.onCakesListClicked("bolos")
            }

            btnBolosEspeciais.setOnClickListener {
                viewModel.onCakesListClicked("bolos_especiais")
            }
        }
    }

    private fun executeAction(action: HomeAction) {
        when (action) {
            is HomeAction.NavigateCakesList -> navigateCakesList(action.type)
            HomeAction.NavigatePromotions -> navigatePromotions()
            HomeAction.ShowErrorMsg -> showMessage("An error occurred. Try again.")
        }
    }

    private fun navigateCakesList(type: String) {
//        val intent = Intent(this, CakesListActivity::class.java).apply {
//            putExtra("cakeType", type) // Adiciona o parâmetro ao Intent
//        }
//        startActivity(intent)
    }

    private fun navigatePromotions() {
//        val intent = Intent(this, PromotionsActivity::class.java)
//        intent.putExtra("promotion", promotion)
//        startActivity(intent)
    }

    private fun showMessage(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }
}