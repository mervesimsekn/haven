package com.example.haven.ui.home

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
import com.example.haven.databinding.ItemProductBinding

class OnSaleAdapter(private val productListener: ProductListener) :
    ListAdapter<Product, OnSaleAdapter.OnSaleProductViewHolder>(OnSaleProductDiffCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnSaleProductViewHolder =
        OnSaleProductViewHolder(
            ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            productListener
        )

    override fun onBindViewHolder(holder: OnSaleProductViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class OnSaleProductViewHolder(
        private val binding: ItemProductBinding,
        private val productListener: ProductListener
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            with(binding) {
                Glide.with(ivBookCover.context).load(product.imageOne).into(ivBookCover)
                tvBookTitle.text = product.title?.substringBefore("-")
                if (product.saleState == true) {
                    tvPrice.text = product.price.toString()
                    tvPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                    tvDiscountedPrice.text = "${product.salePrice.toString()} TL"
                    tvDiscountedPrice.visibility = View.VISIBLE
                    tvDiscountedPrice.setTextColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.warning_color
                        )
                    )
                } else {
                    tvPrice.text = "${product.price.toString()} TL"
                    tvPrice.paintFlags = 0
                    tvDiscountedPrice.visibility = View.GONE
                }

                root.setOnClickListener {
                    productListener.onProductClick(product.id ?: 1)
                }
            }
        }

    }

    class OnSaleProductDiffCallBack : DiffUtil.ItemCallback<Product>() {
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