package com.example.haven.data.source.remote

import com.example.haven.common.Constants.Endpoint.GET_PRODUCTS
import com.example.haven.common.Constants.Endpoint.GET_PRODUCT_DETAIL
import com.example.haven.common.Constants.Endpoint.GET_SALE_PRODUCTS
import com.example.haven.common.Constants.Endpoint.SEARCH_PRODUCT
import com.example.haven.data.model.GetProductDetailResponse
import com.example.haven.data.model.GetProductsResponse
import com.example.haven.data.model.GetSaleProductsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ProductService {

    @GET(GET_PRODUCTS)
    suspend fun getProducts(): GetProductsResponse

    @GET(GET_SALE_PRODUCTS)
    suspend fun getSaleProducts(): GetSaleProductsResponse

    @GET(GET_PRODUCT_DETAIL)
    suspend fun getProductDetail(@Query("id") id: Int): GetProductDetailResponse

    @GET(SEARCH_PRODUCT)
    suspend fun getSearchProducts(@Query("query") query: String): GetProductsResponse
}