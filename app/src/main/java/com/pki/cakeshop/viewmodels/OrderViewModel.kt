package com.pki.cakeshop.viewmodels
import androidx.lifecycle.ViewModel
import com.pki.cakeshop.RetrofitClient
import com.pki.cakeshop.models.Order
import com.pki.cakeshop.repositories.OrderRepository
import retrofit2.Callback
class OrderViewModel: ViewModel() {
    private val orderRepository = OrderRepository(RetrofitClient.orderService);

    // get all pending orders
    fun orders (callback: Callback<List<Order>>) {
        orderRepository.orders(callback)
    }

   fun add (order:Order,callback: Callback<String>){
        orderRepository.add(order,callback)
    }
    fun deny (orderid:String,callback: Callback<String>) {
        orderRepository.deny(orderid,callback)

    }

    fun accept (orderid:String,callback: Callback<String>){
        orderRepository.accept(orderid,callback)

    }

    // notifications for given user id
    fun notifications (id:String,callback:Callback<List<Order>>) {
        orderRepository.notifications(id,callback)
    }

    // update that user was notified
    fun notified (id:String, callback:Callback<String>) {
        orderRepository.notified(id,callback)
    }

    // pending orders for given user id
    fun pending (id:String, callback:Callback<List<Order>>) {
        orderRepository.pending(id,callback)
    }


}