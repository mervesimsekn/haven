package com.example.haven.ui.favorites

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.haven.R
import com.example.haven.data.model.Product
import com.example.haven.databinding.ItemFavoriteBinding

class FavoritesAdapter(
    private val productListener: ProductListener,
    // private val viewModel: CartViewModel
) :
    ListAdapter<Product, FavoritesAdapter.ProductViewHolder>(ProductDiffCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder =
        ProductViewHolder(
            ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            productListener,
            // viewModel
        )

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ProductViewHolder(
        private val binding: ItemFavoriteBinding,
        private val productListener: ProductListener,
        // private val viewModel: CartViewModel
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            with(binding) {
                Glide.with(ivFavoriteImage.context).load(product.imageOne).into(ivFavoriteImage)
                tvFavoriteTitle.text = product.title?.substringBefore("-")
                tvFavoriteAuthor.text = product.title?.substringAfter("-")
                if (product.saleState == true) {
                    tvFavoritePrice.text = product.price.toString()
                    tvFavoritePrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                    tvFavoriteDiscountedPrice.text = "${product.salePrice.toString()} TL"
                    tvFavoriteDiscountedPrice.visibility = View.VISIBLE
                    tvFavoriteDiscountedPrice.setTextColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.warning_color
                        )
                    )
                } else {
                    tvFavoritePrice.text = "${product.price.toString()} TL"
                    tvFavoritePrice.paintFlags = 0
                    tvFavoriteDiscountedPrice.visibility = View.GONE
                }

                ivFavoriteIcon.setOnClickListener {
                    /*
                    val deleteFromCartItem = DeleteFromCartItem(product.id)
                    viewModel.deleteFromCart(deleteFromCartItem)
                    */
                }

                root.setOnClickListener {
                    productListener.onProductClick(product.id ?: 1)
                }
            }
        }
    }

    class ProductDiffCallBack : DiffUtil.ItemCallback<Product>() {
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