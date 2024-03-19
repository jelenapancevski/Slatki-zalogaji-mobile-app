package com.pki.cakeshop.repositories

import com.pki.cakeshop.models.Order
import com.pki.cakeshop.models.OrderData
import com.pki.cakeshop.services.OrderService
import retrofit2.Callback

class OrderRepository(private val orderService:OrderService) {


    fun add (order:OrderData,callback: Callback<String>){
        orderService.add(order).enqueue(callback)
    }

    // notifications for given user id
    fun notifications (id:String,callback:Callback<List<Order>>) {
        orderService.notifications(id).enqueue(callback)
    }

    // update that user was notified
    fun notified (id:String, callback:Callback<String>) {
        orderService.notified(id).enqueue(callback)
    }




}