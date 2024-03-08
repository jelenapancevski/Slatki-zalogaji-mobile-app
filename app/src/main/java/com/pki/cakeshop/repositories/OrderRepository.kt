package com.pki.cakeshop.repositories

import com.pki.cakeshop.models.Order
import com.pki.cakeshop.models.OrderData
import com.pki.cakeshop.services.OrderService
import okhttp3.ResponseBody
import retrofit2.Callback
import java.util.Date

class OrderRepository(private val orderService:OrderService) {

    // get all pending orders
    fun orders (callback: Callback<List<Order>>) {
        orderService.orders().enqueue(callback)
    }

    fun add (order:OrderData,callback: Callback<String>){
        orderService.add(order).enqueue(callback)
    }
    fun deny (orderid:String,callback: Callback<String>) {
        orderService.deny(orderid).enqueue(callback)

    }

    fun accept (orderid:String,callback: Callback<String>){
        orderService.accept(orderid).enqueue(callback)

    }

    // notifications for given user id
    fun notifications (id:String,callback:Callback<List<Order>>) {
        orderService.notifications(id).enqueue(callback)
    }

    // update that user was notified
    fun notified (id:String, callback:Callback<String>) {
        orderService.notified(id).enqueue(callback)
    }

    // pending orders for given user id
    fun pending (id:String, callback:Callback<List<Order>>) {
        orderService.pending(id).enqueue(callback)
    }




}