package com.example.haven.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.haven.data.model.Product
import com.example.haven.databinding.ItemSearchBinding

class SearchAdapter(private val productListener: ProductListener) :
    ListAdapter<Product, SearchAdapter.SearchViewHolder>(SearchDiffCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder =
        SearchViewHolder(
            ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            productListener
        )

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class SearchViewHolder(
        private val binding: ItemSearchBinding,
        private val productListener: ProductListener
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            with(binding) {
                tvSearchTitle.text = product.title?.substringBefore("-")
                tvSearchAuthor.text = product.title?.substringAfter("-")
                root.setOnClickListener {
                    productListener.onProductClick(product.id ?: 1)
                }
            }
        }

    }

    class SearchDiffCallBack : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }

    }

    interface ProductListener {
        fun onProductClick(id: Int)
    }
}