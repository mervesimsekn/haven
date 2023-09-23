package com.example.haven.di

import android.content.Context
import androidx.room.Room
import com.example.haven.data.source.local.ProductRoomDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomDbModule {

    @Provides
    @Singleton
    fun provideRoomDb(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, ProductRoomDB::class.java, "products_room_db")
            .build()

    @Provides
    @Singleton
    fun provideProductDao(roomDB: ProductRoomDB) = roomDB.productDao()
}