package com.pki.cakeshop.repositories

import com.pki.cakeshop.models.Product
import com.pki.cakeshop.models.Promotion
import com.pki.cakeshop.models.User
import com.pki.cakeshop.services.ProductService
import com.pki.cakeshop.services.PromotionService
import okhttp3.ResponseBody
import retrofit2.Callback

class ProductRepository(private val productService:ProductService) {
    fun type(type:String,callback: Callback<List<Product>>) {
        productService.type(type).enqueue(callback)
    }
    fun getImage( name:String, callback: Callback<ResponseBody>) {
        productService.getImage(name).enqueue(callback)
    }
}