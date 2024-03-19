package com.pki.cakeshop.services

import com.pki.cakeshop.models.Order
import com.pki.cakeshop.models.OrderData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface OrderService {

    @POST("/order/add")
    fun add(@Body order: OrderData):Call<String>

    // notifications for given user id
    @FormUrlEncoded
    @POST("/order/notifications")
    fun notifications (@Field("id") id:String): Call<List<Order>>

    // update that user was notified
    @FormUrlEncoded
    @POST("/order/notified")
    fun notified (@Field("id") id:String): Call<String>


}