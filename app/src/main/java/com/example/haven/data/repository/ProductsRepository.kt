package com.example.haven.data.repository


import com.example.haven.common.Resource
import com.example.haven.data.mapper.mapToProductEntity
import com.example.haven.data.mapper.mapToProductUI
import com.example.haven.data.model.Product
import com.example.haven.data.model.ProductUI
import com.example.haven.data.source.local.ProductDao
import com.example.haven.data.source.remote.ProductService

class ProductsRepository(
    private val productService: ProductService,
    private val productDao: ProductDao
) {

    suspend fun getProducts(): Resource<List<Product>> {
        return try {
            val result = productService.getProducts().products
            if (result.isNullOrEmpty()) {
                Resource.Error(Exception("Products Not Found"))
            } else {
                Resource.Success(result)
            }
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    suspend fun getSaleProducts(): Resource<List<Product>> {
        return try {
            val result = productService.getSaleProducts().products
            if (result.isNullOrEmpty()) {
                Resource.Error(Exception("Sale Products Not Found"))
            } else {
                Resource.Success(result)
            }
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    suspend fun getProductDetail(id: Int): Resource<ProductUI> {
        return try {
            val result = productService.getProductDetail(id).product
            result?.let {
                Resource.Success(it.mapToProductUI())
            } ?: kotlin.run {
                Resource.Error(Exception("Product Not Found"))
            }
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    suspend fun getSearchResults(query: String): Resource<List<Product>> {

        return try {
            val result = productService.getSearchProducts(query).products
            if (result.isNullOrEmpty()) {
                Resource.Error(Exception("Products Not Found"))
            } else {
                Resource.Success(result)
            }
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }
/*

        suspend fun addProductToFavorites(product: ProductUI) {
            productDao.insertProduct(product.mapToProductEntity())
        }

        suspend fun deleteProductFromFavorites(product: ProductUI) {
            productDao.deleteProduct(product.mapToProductEntity())
        }

        suspend fun getFavorites(): Resource<List<ProductUI>> {
            return try {
                val result = productDao.getProducts().map { it.mapToProductUI() }
                if (result.isEmpty()) {
                    Resource.Error(Exception("Favorite Products Not Found"))
                } else {
                    Resource.Success(result)
                }
                Resource.Success(result)
            } catch (e: Exception) {
                Resource.Error(e)
            }
        }
*/

}