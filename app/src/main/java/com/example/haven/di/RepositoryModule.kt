package com.example.haven.di

import com.example.haven.data.repository.AuthenticationRepository
import com.example.haven.data.repository.CartRepository
import com.example.haven.data.repository.ProductsRepository
import com.example.haven.data.source.local.ProductDao
import com.example.haven.data.source.remote.CartService
import com.example.haven.data.source.remote.ProductService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideProductRepository(productService: ProductService, productDao: ProductDao) : ProductsRepository = ProductsRepository(productService, productDao)

    @Provides
    @Singleton
    fun provideCartRepository(cartService: CartService) : CartRepository = CartRepository(cartService)

    @Provides
    @Singleton
    fun provideAuthenticationRepository() : AuthenticationRepository = AuthenticationRepository()
}