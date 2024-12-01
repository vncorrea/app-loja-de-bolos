package com.example.app_loja_de_bolos.shoplist.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.app_loja_de_bolos.databinding.ActivityShoplistBinding
import com.example.app_loja_de_bolos.shoplist.model.CartItem
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel

class ShoplistActivity<ActivityShoplistBinding> : AppCompatActivity() {

    private lateinit var binding: ActivityShoplistBinding
    private val viewModel: ShoplistViewModel by viewModel()
    private val cartAdapter = ShoplistAdapter { item -> onRemoveItem(item) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShoplistBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        observeViewModel()
        setupListeners()
    }

    private fun setupRecyclerView() {
        binding.cartRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@ShoplistActivity)
            adapter = cartAdapter
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launchWhenStarted {
            viewModel.cartItems.collect { items ->
                cartAdapter.submitList(items)
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.cartTotal.collect { total ->
                binding.cartTotalText.text = "Total: R$ %.2f".format(total)
            }
        }
    }

    private fun setupListeners() {
        binding.checkoutButton.setOnClickListener {
            // Ação para finalizar o pedido
            viewModel.clearCart()
            finish()
        }
    }

    private fun onRemoveItem(item: CartItem) {
        viewModel.removeItemFromCart(item.id)
    }
}
