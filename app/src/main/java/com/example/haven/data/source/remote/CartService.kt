package com.example.haven.data.source.remote

import com.example.haven.common.Constants.Endpoint.ADD_TO_CART
import com.example.haven.common.Constants.Endpoint.CLEAR_CART
import com.example.haven.common.Constants.Endpoint.DELETE_FROM_CART
import com.example.haven.common.Constants.Endpoint.GET_CART_PRODUCTS
import com.example.haven.data.model.AddToCartResponse
import com.example.haven.data.model.CartItem
import com.example.haven.data.model.ClearCartResponse
import com.example.haven.data.model.DeleteFromCartItem
import com.example.haven.data.model.DeleteFromCartResponse
import com.example.haven.data.model.GetProductsResponse
import com.example.haven.data.model.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface CartService {

    @POST(ADD_TO_CART)
    suspend fun addToCart(@Body cartItem: CartItem): AddToCartResponse

    @GET(GET_CART_PRODUCTS)
    suspend fun getCartProducts(@Query("userId") userId: String?): GetProductsResponse

    @POST(DELETE_FROM_CART)
    suspend fun deleteFromCart(@Body deleteFromCartItem: DeleteFromCartItem): DeleteFromCartResponse

    @POST(CLEAR_CART)
    suspend fun clearCart(@Body user: User): ClearCartResponse
}