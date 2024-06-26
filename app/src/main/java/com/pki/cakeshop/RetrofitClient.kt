package com.pki.cakeshop

import android.util.Log
import com.pki.cakeshop.services.OrderService
import com.pki.cakeshop.services.ProductService
import com.pki.cakeshop.services.PromotionService
import com.pki.cakeshop.services.UserService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:4000/"

    private val loggingInterceptor = HttpLoggingInterceptor { message ->
        Log.d("Retrofit", message)
    }.apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL).client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val promotionService: PromotionService by lazy {
        retrofit.create(PromotionService::class.java)
    }
    val userService: UserService by lazy {
        retrofit.create(UserService::class.java)
    }
    val productService: ProductService by lazy {
        retrofit.create(ProductService::class.java)
    }
    val orderService: OrderService by lazy {
        retrofit.create(OrderService::class.java)
    }
}