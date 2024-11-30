package com.example.app_loja_de_bolos.cake_category_list.presentation

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.app_loja_de_bolos.databinding.ActivityCakeListCategoryBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class CakeCategoryListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCakeListCategoryBinding
    private val viewModel: CakeCategoryListViewModel by viewModel()
    private lateinit var adapter: CakeCategoryListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("CakeListActivity", "onCreate")
        super.onCreate(savedInstanceState)
        binding = ActivityCakeListCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvCakeCategories.layoutManager = LinearLayoutManager(this)

        adapter = CakeCategoryListAdapter(mutableListOf()) { category ->
            Toast.makeText(this, "Categoria selecionada: $category", Toast.LENGTH_SHORT).show()
        }

        binding.rvCakeCategories.adapter = adapter

        val cakeType = intent.getStringExtra("cakeType")
        Log.d("CakeListActivity", "CakeType: $cakeType")

        observeCategories()

        viewModel.fetchCakeCategories(cakeType)
    }

    private fun observeCategories() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.categories.collect { categories ->
                    Log.d("CakeListActivity", "Observed categories: $categories")
                    adapter.updateData(categories)
                }
            }
        }
    }
}