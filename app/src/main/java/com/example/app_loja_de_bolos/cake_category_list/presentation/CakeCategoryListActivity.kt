package com.example.app_loja_de_bolos.cake_category_list.presentation

import CakeCategoryAction
import CakeCategoryListAdapter
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
import com.example.app_loja_de_bolos.cake_category_list.model.CakeCategory
import com.example.app_loja_de_bolos.cake_list.presentation.CakeListAction
import com.example.app_loja_de_bolos.cake_list.presentation.CakeListActivity
import com.example.app_loja_de_bolos.databinding.ActivityCakeListCategoryBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class CakeCategoryListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCakeListCategoryBinding
    private val viewModel: CakeCategoryListViewModel by viewModel()
    private lateinit var adapter: CakeCategoryListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCakeListCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupRecyclerView()

        val cakeType = intent.getStringExtra("cakeType")

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

        viewModel.fetchCakeCategories(cakeType)

        binding.fabCart.setOnClickListener {
            navigateToShoppingList()
        }
    }

    private fun setupRecyclerView() {
        adapter = CakeCategoryListAdapter(mutableListOf()) { category ->
            viewModel.onClickCakeCategory(category.name)
        }

        binding.rvCakeCategories.layoutManager = LinearLayoutManager(this)
        binding.rvCakeCategories.adapter = adapter
    }

    private fun executeAction(action: CakeCategoryAction) {
        when (action) {
            is CakeCategoryAction.UpdateCategoryList -> updateCategories(action.categories)
            CakeCategoryAction.ShowErrorMsg -> showMessage("Erro ao carregar categorias.")
            is CakeCategoryAction.NavigateToCakeList -> navigateToCakeList(action.category)
            is CakeCategoryAction.UiState -> handleLoadingState(action.isLoading)
            else -> {
                Log.d("CakeCategoryListActivity", "Ação não reconhecida.")
            }
        }
    }

    private fun handleLoadingState(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = android.view.View.VISIBLE
            binding.rvCakeCategories.visibility = android.view.View.GONE
        } else {
            binding.progressBar.visibility = android.view.View.GONE
            binding.rvCakeCategories.visibility = android.view.View.VISIBLE
        }
    }

    private fun navigateToCakeList(category: String) {
        val cakeType = intent.getStringExtra("cakeType")

        val intent = Intent(this, CakeListActivity::class.java).apply {
            putExtra("cakeCategory", category)
            putExtra("cakeType", cakeType)
        }

        startActivity(intent)
    }

    private fun navigateToShoppingList() {
        val intent = Intent(this, com.example.app_loja_de_bolos.shopping_list.presentation.ShoppingListActivity::class.java)
        startActivity(intent)
    }

    private fun updateCategories(categories: List<CakeCategory>) {
        adapter.updateData(categories)
    }

    private fun showMessage(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }
}