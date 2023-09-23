package com.example.haven.ui.cart

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
import com.example.haven.data.model.DeleteFromCartItem
import com.example.haven.data.model.Product
import com.example.haven.databinding.ItemCartBinding

class CartAdapter(
    private val productListener: ProductListener,
    private val viewModel: CartViewModel
) :
    ListAdapter<Product, CartAdapter.CartViewHolder>(ProductDiffCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder =
        CartViewHolder(
            ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            productListener,
            viewModel
        )

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class CartViewHolder(
        private val binding: ItemCartBinding,
        private val productListener: ProductListener,
        private val viewModel: CartViewModel
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            with(binding) {
                Glide.with(ivCartImage.context).load(product.imageOne).into(ivCartImage)
                tvCartTitle.text = product.title?.substringBefore("-")
                tvCartAuthor.text = product.title?.substringAfter("-")
                if (product.saleState == true) {
                    tvCartPrice.text = product.price.toString()
                    tvCartPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                    tvCartDiscountedPrice.text = "${product.salePrice.toString()} TL"
                    tvCartDiscountedPrice.visibility = View.VISIBLE
                    tvCartDiscountedPrice.setTextColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.warning_color
                        )
                    )
                } else {
                    tvCartPrice.text = "${product.price.toString()} TL"
                    tvCartPrice.paintFlags = 0
                    tvCartDiscountedPrice.visibility = View.GONE
                }

                ivCartDeleteItem.setOnClickListener {
                    val deleteFromCartItem = DeleteFromCartItem(product.id)
                    viewModel.deleteFromCart(deleteFromCartItem)
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