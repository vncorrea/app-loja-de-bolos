package com.example.app_loja_de_bolos.cake_list.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.app_loja_de_bolos.cake_details.presentation.CakeDetailsActivity
import com.example.app_loja_de_bolos.cake_list.model.Cake
import com.example.app_loja_de_bolos.databinding.ActivityCakeListBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class CakeListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCakeListBinding
    private val viewModel: CakeListViewModel by viewModel()
    private lateinit var adapter: CakeListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCakeListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupRecyclerView()

        val cakeType = intent.getStringExtra("cakeType")
        val cakeCategory = intent.getStringExtra("cakeCategory")

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiAction.collect { action ->
                    action?.let {
                        executeAction(it)
                        viewModel.resetUiAction()
                    }
                }
            }
        }

        viewModel.fetchCakeList(cakeType, cakeCategory)
    }

    private fun setupRecyclerView() {
        adapter = CakeListAdapter(mutableListOf()) { cake ->
            viewModel.onClickCake(cake)
        }

        binding.rvCakes.layoutManager = LinearLayoutManager(this)
        binding.rvCakes.adapter = adapter
    }

    private fun handleLoadingState(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = android.view.View.VISIBLE
            binding.rvCakes.visibility = android.view.View.GONE
        } else {
            binding.progressBar.visibility = android.view.View.GONE
            binding.rvCakes.visibility = android.view.View.VISIBLE
        }
    }

    private fun executeAction(action: CakeListAction) {
        when (action) {
            is CakeListAction.UpdateCakeList -> updateCakeList(action.cakes)
            CakeListAction.ShowErrorMsg -> showMessage("Erro ao carregar categorias.")
            is CakeListAction.NavigateToCakeDetails -> navigateToCakeDetails(action.cake);
            is CakeListAction.UiState -> handleLoadingState(action.isLoading)

            else -> {
                Log.d("CakeListActivity", "Ação não reconhecida.")
            }
        }
    }

    private fun navigateToCakeDetails(cake: Cake) {
        val cakeType = intent.getStringExtra("cakeType")
        val cakeCategory = intent.getStringExtra("cakeCategory")

        val intent = Intent(this, CakeDetailsActivity::class.java).apply {
            putExtra("cakeId", cake.id)
            putExtra("cakeCategory", cakeCategory)
            putExtra("cakeType", cakeType)
        }

        startActivity(intent)
    }

     fun updateCakeList(cakes: List<Cake>) {
        adapter.updateData(cakes)
    }

    private fun showMessage(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }
}