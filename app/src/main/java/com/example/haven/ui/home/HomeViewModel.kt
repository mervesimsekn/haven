package com.example.haven.ui.home


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.haven.common.Resource
import com.example.haven.data.model.Product
import com.example.haven.data.repository.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val productsRepository: ProductsRepository) :
    ViewModel() {

    private var _homeState = MutableLiveData<HomeState>()
    val homeState: LiveData<HomeState>
        get() = _homeState

    fun getProducts() {
        viewModelScope.launch {
            _homeState.value = HomeState.Loading
            when (val result = productsRepository.getProducts()) {
                is Resource.Success -> {
                    _homeState.value = HomeState.Data(result.data)
                }

                is Resource.Error -> {
                    _homeState.value = HomeState.Error(result.throwable)
                }
            }
        }
    }

    fun getSaleProducts() {
        viewModelScope.launch {
            _homeState.value = HomeState.Loading
            when (val result = productsRepository.getSaleProducts()) {
                is Resource.Success -> {
                    _homeState.value = HomeState.SaleData(result.data)
                }

                is Resource.Error -> {
                    _homeState.value = HomeState.Error(result.throwable)
                }
            }
        }
    }
}

sealed interface HomeState {
    data object Loading : HomeState
    data class Data(val products: List<Product>) : HomeState
    data class SaleData(val products: List<Product>) : HomeState
    data class Error(val throwable: Throwable) : HomeState
}