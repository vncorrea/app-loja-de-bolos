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
            is CakeCategoryAction.NavigateToCakeList -> navigateToCakeList(action.category);
            else -> {
                Log.d("CakeListActivity", "Ação não reconhecida.")
            }
        }
    }

    private fun navigateToCakeList(category: String) {
        val intent = Intent(this, CakeCategoryListActivity::class.java).apply {
            putExtra("category", category)
        }

        startActivity(intent)
    }

    private fun updateCategories(categories: List<CakeCategory>) {
        adapter.updateData(categories)
    }

    private fun showMessage(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }
}