package com.example.app_loja_de_bolos.cake_list.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.app_loja_de_bolos.R
import com.example.app_loja_de_bolos.cake_list.model.Cake
import com.example.app_loja_de_bolos.databinding.ActivityCakeListItemBinding

class CakeListAdapter(
    private val cakes: MutableList<Cake>,
    private val onCakeClicked: (Cake) -> Unit
) : RecyclerView.Adapter<CakeListAdapter.CakeListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CakeListViewHolder {
        val binding = ActivityCakeListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CakeListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CakeListViewHolder, position: Int) {
        holder.bind(cakes[position])
    }

    override fun getItemCount(): Int = cakes.size

    fun updateData(newCakes: List<Cake>) {
        cakes.clear()
        cakes.addAll(newCakes)
        notifyDataSetChanged()
    }

    inner class CakeListViewHolder(
        private val binding: ActivityCakeListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(cake: Cake) {
            binding.tvNome.text = cake.formattedName
            binding.tvDescricao.text = cake.description
            binding.tvPreco.text = cake.value

            Log.d("CakeListAdapter", "bind: ${cake.imageUrl}")

            Glide.with(binding.root.context)
                .load(cake.imageUrl)
//                .placeholder(R.drawable.placeholder)
//                .error(R.drawable.error_image)
                .into(binding.ivBolo)

            binding.root.setOnClickListener {
                onCakeClicked(cake)
            }
        }
    }
}