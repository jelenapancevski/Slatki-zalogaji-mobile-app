package com.pki.cakeshop.repositories

import com.pki.cakeshop.models.CommentData
import com.pki.cakeshop.models.Product
import com.pki.cakeshop.services.ProductService
import okhttp3.ResponseBody
import retrofit2.Callback


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

    fun comment (comment:CommentData,callback:Callback<String>){
        productService.comment(comment).enqueue(callback)
    }

    // adds new product
    fun add(product:Product, callback: Callback<String>){
        productService.add(product).enqueue(callback)
    }
}