package com.example.haven.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.haven.common.Resource
import com.example.haven.data.model.CartItem
import com.example.haven.data.model.ProductUI
import com.example.haven.data.repository.CartRepository
import com.example.haven.data.repository.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val productsRepository: ProductsRepository,
    private val cartRepository: CartRepository
) : ViewModel() {

    private var _detailState = MutableLiveData<DetailState>()
    val detailState: LiveData<DetailState>
        get() = _detailState

    fun getProductDetail(id: Int) {
        viewModelScope.launch {
            _detailState.value = DetailState.Loading
            when (val result = productsRepository.getProductDetail(id)) {
                is Resource.Success -> {
                    _detailState.value = DetailState.Data(result.data)
                }

                is Resource.Error -> {
                    _detailState.value = DetailState.Error(result.throwable)
                }
            }
        }
    }

    fun addToCart(cartItem: CartItem) {
        viewModelScope.launch {
            _detailState.value = DetailState.Loading
            when (val result = cartRepository.addToCart(cartItem)) {
                is Resource.Error -> {
                    _detailState.value = DetailState.Error(result.throwable)
                }

                is Resource.Success -> {
                    _detailState.value = DetailState.AddProduct(result.data)
                }
            }
        }
    }
/*
    fun addProductToFavorites(product: ProductUI) {
        viewModelScope.launch {
            productsRepository.addProductToFavorites(product)
        }
    }

*/
}

sealed interface DetailState {
    data object Loading : DetailState
    data class Data(val product: ProductUI) : DetailState
    data class AddProduct(val message: String) : DetailState
    data class Error(val throwable: Throwable) : DetailState
}