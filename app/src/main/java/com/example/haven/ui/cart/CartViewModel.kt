package com.example.haven.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.haven.common.Resource
import com.example.haven.data.model.DeleteFromCartItem
import com.example.haven.data.model.Product
import com.example.haven.data.model.User
import com.example.haven.data.repository.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(private val cartRepository: CartRepository) : ViewModel() {

    private var _cartState = MutableLiveData<CartState>()
    val cartState: LiveData<CartState>
        get() = _cartState

    fun getCartProducts(userId: String) {
        viewModelScope.launch {
            when (val result = cartRepository.getCartProducts(userId)) {
                is Resource.Success -> {
                    _cartState.value = CartState.Data(result.data)
                }

                is Resource.Error -> {
                    _cartState.value = CartState.Error(result.throwable)
                }
            }
        }
    }

    fun clearCart(user: User) {
        viewModelScope.launch {
            when (val result = cartRepository.clearCart(user)) {
                is Resource.Error -> {
                    _cartState.value = CartState.Error(result.throwable)
                }

                is Resource.Success -> {
                    _cartState.value = CartState.ClearCart(result.data)
                }
            }
        }
    }

    fun deleteFromCart(deleteFromCartItem: DeleteFromCartItem) {
        viewModelScope.launch {
            when (val result = cartRepository.deleteFromCart(deleteFromCartItem)) {
                is Resource.Error -> {
                    _cartState.value = CartState.Error(result.throwable)
                }

                is Resource.Success -> {
                    _cartState.value = CartState.DeleteProduct(result.data, deleteFromCartItem) }
            }
        }
    }

}

sealed interface CartState {
    data class Data(val products: List<Product>) : CartState
    data class DeleteProduct(val message: String, val deleteFromCartItem: DeleteFromCartItem) : CartState
    data class ClearCart(val message: String) : CartState
    data class Error(val throwable: Throwable) : CartState
}