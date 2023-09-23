package com.example.haven

import android.app.Application
import com.example.haven.common.Constants.BASE_URL
import com.example.haven.data.source.remote.CartService
import com.example.haven.data.source.remote.ProductService
import dagger.hilt.android.HiltAndroidApp
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@HiltAndroidApp
class MainApplication : Application()