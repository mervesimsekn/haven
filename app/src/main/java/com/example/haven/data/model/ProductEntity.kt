package com.example.haven.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "liked_products")
data class ProductEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id")
    val id: Int?,

    @ColumnInfo("title")
    val title: String?,

    @ColumnInfo("description")
    val description: String?,

    @ColumnInfo("category")
    val category: String?,

    @ColumnInfo("imageOne")
    val imageOne: String?,

    @ColumnInfo("imageTwo")
    val imageTwo: String?,

    @ColumnInfo("imageThree")
    val imageThree: String?,

    @ColumnInfo("price")
    val price: Double?,

    @ColumnInfo("saleState")
    val saleState: Boolean?,

    @ColumnInfo("salePrice")
    val salePrice: Double?,

    @ColumnInfo("rate")
    val rate: Double?,

    @ColumnInfo("count")
    val count: Int?,

    )
