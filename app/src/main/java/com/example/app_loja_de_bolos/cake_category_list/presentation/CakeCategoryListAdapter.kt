package com.example.app_loja_de_bolos.cake_category_list.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.app_loja_de_bolos.databinding.ActivityCakeListCategoryItemBinding

class CakeCategoryListAdapter(
    private var categories: MutableList<String>, // Alterado para MutableList<String>
    private val onCategoryClick: (String) -> Unit
) : RecyclerView.Adapter<CakeCategoryListAdapter.CakeCategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CakeCategoryViewHolder {
        val binding = ActivityCakeListCategoryItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CakeCategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CakeCategoryViewHolder, position: Int) {
        Log.d("CakeCategoryListAdapter", "Position: $position")
        val category = categories[position]
        holder.bind(category, onCategoryClick)
    }

    override fun getItemCount(): Int = categories.size

    fun updateData(newCategories: List<String>) {
        categories.clear()
        categories.addAll(newCategories)
        notifyDataSetChanged()
    }

    class CakeCategoryViewHolder(
        private val binding: ActivityCakeListCategoryItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(category: String, onCategoryClick: (String) -> Unit) {
            Log.d("CakeCategoryViewHolder", "Category: $category")
            binding.tvCategoryName.text = category
            binding.root.setOnClickListener { onCategoryClick(category) }
        }
    }
}