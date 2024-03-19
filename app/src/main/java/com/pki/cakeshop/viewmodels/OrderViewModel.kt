package com.pki.cakeshop.viewmodels
import androidx.lifecycle.ViewModel
import com.pki.cakeshop.RetrofitClient
import com.pki.cakeshop.models.Order
import com.pki.cakeshop.models.OrderData
import com.pki.cakeshop.repositories.OrderRepository
import retrofit2.Callback
class OrderViewModel: ViewModel() {
    private val orderRepository = OrderRepository(RetrofitClient.orderService)


   fun add (order:OrderData,callback: Callback<String>){
        orderRepository.add(order,callback)
    }

    // notifications for given user id
    fun notifications (id:String,callback:Callback<List<Order>>) {
        orderRepository.notifications(id,callback)
    }

    // update that user was notified
    fun notified (id:String, callback:Callback<String>) {
        orderRepository.notified(id,callback)
    }


}