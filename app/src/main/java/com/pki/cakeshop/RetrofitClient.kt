package com.pki.cakeshop

import android.util.Log
import com.pki.cakeshop.models.Promotion
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
    // Create a logging interceptor for debugging purposes
    val loggingInterceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
        override fun log(message: String) {
            Log.d("Retrofit", message) // Custom tag "Retrofit" is used here
        }
    }).apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        // Add any other OkHttpClient configurations if needed
        .build()
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL).client(client)
            .addConverterFactory(GsonConverterFactory.create())
            // Add any other configurations if needed (e.g., OkHttpClient with interceptors)
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