package com.example.app_loja_de_bolos.shopping_list.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.app_loja_de_bolos.R
import com.example.app_loja_de_bolos.cake_list.model.Cake
import com.example.app_loja_de_bolos.databinding.ActivityCakeListItemBinding
import com.example.app_loja_de_bolos.databinding.ActivityShoppingListItemBinding
import com.example.app_loja_de_bolos.shopping_list.model.CartItem

class ShoppingListAdapter(
    private val items: MutableList<CartItem>,
    private val onDeleteItemClick: (CartItem) -> Unit
) : RecyclerView.Adapter<ShoppingListAdapter.ShoppingListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingListViewHolder {
        val binding = ActivityShoppingListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ShoppingListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShoppingListViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun updateData(newCartItems: List<CartItem>) {
        items.clear()
        items.addAll(newCartItems)
        notifyDataSetChanged()
    }

    inner class ShoppingListViewHolder(
        private val binding: ActivityShoppingListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(cartItem: CartItem) {
            binding.itemName.text = cartItem.name
            binding.itemPrice.text = cartItem.value
            binding.itemQuantity.text = cartItem.quantity.toString()

            Log.d("CakeListAdapter", "bind: ${cartItem.imageUrl}")

            Glide.with(binding.root.context)
                .load(cartItem.imageUrl)
                .into(binding.itemImage)

//            binding.root.setOnClickListener {
//                onCakeClicked(cake)
//            }
        }
    }
}