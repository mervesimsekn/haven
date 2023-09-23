package com.example.haven.data.repository

import com.example.haven.common.Resource
import com.example.haven.data.model.CartItem
import com.example.haven.data.model.DeleteFromCartItem
import com.example.haven.data.model.Product
import com.example.haven.data.model.User
import com.example.haven.data.source.remote.CartService


class CartRepository(private val cartService: CartService) {

    suspend fun addToCart(cartItem: CartItem): Resource<String> {
        return try {
            val result = cartService.addToCart(cartItem).message
            result?.let {
                Resource.Success(it)
            } ?: kotlin.run {
                Resource.Error(Exception("Failed to Add The Product to The Cart"))
            }
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    suspend fun getCartProducts(userId: String): Resource<List<Product>> {
        return try {
            val result = cartService.getCartProducts(userId).products
            if (result == null) {
                Resource.Error(Exception("Cart Products Not Found"))
            } else {
                Resource.Success(result)
            }
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    suspend fun clearCart(user: User): Resource<String> {
        return try {
            val result = cartService.clearCart(user).message
            result?.let {
                Resource.Success(it)
            } ?: kotlin.run {
                Resource.Error(Exception("Failed to Clear The Cart"))
            }
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    suspend fun deleteFromCart(deleteFromCartItem: DeleteFromCartItem): Resource<String> {
        return try {
            val result = cartService.deleteFromCart(deleteFromCartItem).message
            result?.let {
                Resource.Success(it)
            } ?: kotlin.run {
                Resource.Error(Exception("Failed to Delete The Product From The Cart"))
            }
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }
}