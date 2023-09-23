package com.example.haven.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.haven.data.model.ProductEntity

@Database(entities = [ProductEntity::class], version = 1)
abstract class ProductRoomDB : RoomDatabase() {

    abstract fun productDao(): ProductDao
}

