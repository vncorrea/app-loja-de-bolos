import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.app_loja_de_bolos.cake_category_list.model.CakeCategory
import com.example.app_loja_de_bolos.databinding.ActivityCakeListCategoryItemBinding

class CakeCategoryListAdapter(
    private val categories: MutableList<CakeCategory>,
    private val onCategoryClicked: (CakeCategory) -> Unit
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
        holder.bind(categories[position])
    }

    override fun getItemCount(): Int = categories.size

    fun updateData(newCategories: List<CakeCategory>) {
        categories.clear()
        categories.addAll(newCategories)
        notifyDataSetChanged()
    }

    inner class CakeCategoryViewHolder(
        private val binding: ActivityCakeListCategoryItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(category: CakeCategory) {
            Log.d("CakeCategoryListAdapter", "Binding category: $category")
            binding.tvCategoryName.text = category.formattedName

            Glide.with(binding.root.context)
                .load(category.imageUrl)
//                .placeholder(R.drawable.placeholder)
//                .error(R.drawable.error_image)
                .into(binding.ivCategoryImage)


            binding.root.setOnClickListener {
                onCategoryClicked(category)
            }
        }
    }
}