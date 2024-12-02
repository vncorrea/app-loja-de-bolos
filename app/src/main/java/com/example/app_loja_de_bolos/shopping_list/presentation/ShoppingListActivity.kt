package com.example.app_loja_de_bolos.shopping_list.presentation

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
import com.example.app_loja_de_bolos.databinding.ActivityShoppingListBinding
import com.example.app_loja_de_bolos.home.presentation.HomeActivity
import com.example.app_loja_de_bolos.shopping_list.model.CartItem
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class ShoppingListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShoppingListBinding
    private val viewModel: ShoppingListViewModel by viewModel()
    private lateinit var adapter: ShoppingListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShoppingListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicialize o RecyclerView e o adapter antes de qualquer uso
        setupRecyclerView()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.cartTotal.collect { total ->
                        binding.cartTotalText.text = formatToCurrency(total)
                    }
                }
                launch {
                    viewModel.uiAction.collect { action ->
                        action?.let {
                            executeAction(it)
                            viewModel.resetUiAction()
                        }
                    }
                }
            }
        }

        // Carrega os itens do carrinho
        viewModel.fetchShoppingList()
    }

    private fun formatToCurrency(value: Double): String {
        return String.format("R$ %.2f", value).replace(".", ",")
    }

    private fun setupRecyclerView() {
        adapter = ShoppingListAdapter(
            mutableListOf(),
            { cartItem ->
                viewModel.onDeleteItemClick(cartItem.id)
            },
            { cartItem, newQuantity ->
                viewModel.updateItemQuantity(cartItem, newQuantity)
            }
        )

        binding.cartRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.cartRecyclerView.adapter = adapter
    }

    private fun handleLoadingState(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = android.view.View.VISIBLE
            binding.cartRecyclerView.visibility = android.view.View.GONE
        } else {
            binding.progressBar.visibility = android.view.View.GONE
            binding.cartRecyclerView.visibility = android.view.View.VISIBLE
        }
    }

    private fun executeAction(action: ShoppingListAction) {
        when (action) {
            is ShoppingListAction.updateCartList -> updateShoppingList(action.cartItems)
            ShoppingListAction.ShowErrorMsg -> showMessage("Erro ao carregar categorias.")
            is ShoppingListAction.NavigateToHome -> navigateToHome()
            is ShoppingListAction.UiState -> handleLoadingState(action.isLoading)
            else -> Log.d("ShoppingListActivity", "Ação não reconhecida.")
        }
    }

    private fun navigateToHome() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }

    private fun updateShoppingList(cartItems: List<CartItem>) {
        adapter.updateData(cartItems)
    }

    private fun showMessage(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }
}