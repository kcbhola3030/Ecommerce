package com.example.ecommerce.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ecommerce.databinding.ItemCategoryBinding
import com.example.ecommerce.model.data.Category

class CategoryAdapter(private var categories: List<Category>,
                      private val onCategoryClick: (Category) -> Unit): RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryViewHolder {
        val binding = ItemCategoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CategoryViewHolder,
        position: Int
    ) {
        holder.bind(categories[position])
    }

    override fun getItemCount() = categories.size


    inner class CategoryViewHolder(val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(category: Category) {
            binding.tvCategoryName.text = category.category_name

            val imageUrl = "http://103.163.198.93/myshop/images/${category.category_image_url}"


            Glide.with(binding.root.context)
                .load(imageUrl)           // ← full image URL
                .placeholder(android.R.drawable.ic_menu_gallery)  // shows while loading
                .error(android.R.drawable.ic_menu_report_image)   // shows if fails
                .into(binding.ivCategory)

            binding.root.setOnClickListener {
                onCategoryClick(category)
            }


        }
    }
    fun updateList(newList: List<Category>) {
        categories = newList
        notifyDataSetChanged()
    }

}
