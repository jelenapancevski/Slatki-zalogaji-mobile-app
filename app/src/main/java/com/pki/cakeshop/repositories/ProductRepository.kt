package com.pki.cakeshop.repositories

import com.pki.cakeshop.models.Product
import com.pki.cakeshop.models.Promotion
import com.pki.cakeshop.models.User
import com.pki.cakeshop.services.ProductService
import com.pki.cakeshop.services.PromotionService
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import java.util.Date

class ProductRepository(private val productService:ProductService) {
    fun get (callback:Callback<List<Product>>){
        productService.get().enqueue(callback)
    }
    fun type(type:String,callback: Callback<List<Product>>) {
        productService.type(type).enqueue(callback)
    }
    fun getImage( name:String, callback: Callback<ResponseBody>) {
        productService.getImage(name).enqueue(callback)
    }
    // returns info of specific product by id

    fun product (id:String, callback: Callback<Product>){
        productService.product(id).enqueue(callback)
    }

    // adds comment to product given by id

    fun comment (username:String, date: Date, callback:Callback<String>){
        productService.comment(username,date).enqueue(callback)
    }

    // adds new product
    fun add(product:Product, callback: Callback<String>){
        productService.add(product).enqueue(callback)
    }
}