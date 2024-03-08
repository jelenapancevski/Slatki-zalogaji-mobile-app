package com.pki.cakeshop.services

import com.pki.cakeshop.models.Order
import com.pki.cakeshop.models.OrderData
import com.pki.cakeshop.models.Product
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import java.util.Date

interface OrderService {
    @GET("/order/orders")
    fun orders():Call<List<Order>>
    @POST("/order/add")
    fun add(@Body order: OrderData):Call<String>
    @FormUrlEncoded
    @POST("/order/deny")
    fun deny(@Field("orderid")orderid:String):Call<String>
    @FormUrlEncoded
    @POST("/order/accept")
    fun accept(@Field("orderid")orderid:String):Call<String>

    // notifications for given user id
    @FormUrlEncoded
    @POST("/order/notifications")
    fun notifications (@Field("id") id:String): Call<List<Order>>

    // update that user was notified
    @FormUrlEncoded
    @POST("/order/notified")
    fun notified (@Field("id") id:String): Call<String>

    // pending orders for given user id
    @FormUrlEncoded
    @POST("/order/pending")
    fun pending (@Field("id") id:String): Call<List<Order>>


}