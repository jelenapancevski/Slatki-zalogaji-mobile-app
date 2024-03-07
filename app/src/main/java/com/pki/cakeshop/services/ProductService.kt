package com.pki.cakeshop.services

import com.pki.cakeshop.models.Comment
import com.pki.cakeshop.models.CommentData
import com.pki.cakeshop.models.Product
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import java.util.Date

interface ProductService {
    @GET("product/get")
    fun get () :Call<List<Product>>
    @GET("/api/products/{name}")
    fun getImage(@Path("name") name: String): Call<ResponseBody>
    @FormUrlEncoded
    @POST("product/type")
    fun type(@Field("type") type:String):Call<List<Product>>

    // returns info of specific product by id
    @FormUrlEncoded
    @POST("product/product")
    fun product (@Field ("id")id:String):Call<Product>

    // adds comment to product given by id
    @POST("product/comment")
   fun comment (@Body comment:CommentData):Call<String>

    // adds new product
    @FormUrlEncoded
    @POST("product/add")
    fun add(@Body product:Product): Call <String>

}