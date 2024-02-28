package com.pki.cakeshop.services

import com.pki.cakeshop.models.Product
import com.pki.cakeshop.models.Promotion
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface ProductService {
    @GET("/api/products/{name}")
    fun getImage(@Path("name") name: String): Call<ResponseBody>
    @FormUrlEncoded
    @POST("product/type")
    fun type(@Field("type") type:String):Call<List<Product>>
}