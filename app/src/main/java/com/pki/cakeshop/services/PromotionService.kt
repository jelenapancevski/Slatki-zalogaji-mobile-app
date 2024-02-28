package com.pki.cakeshop.services

import com.pki.cakeshop.models.Promotion
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface PromotionService {
    @GET("/api/promotions/{name}")
    fun getImage(@Path("name") name: String): Call<ResponseBody>
    @Headers("Content-Type:application/json")
    @GET("promotion/get")
    fun get():Call<List<Promotion>>
}