package com.pki.cakeshop.viewmodels
import androidx.lifecycle.ViewModel
import com.pki.cakeshop.RetrofitClient
import com.pki.cakeshop.models.CommentData
import com.pki.cakeshop.models.Product
import com.pki.cakeshop.repositories.ProductRepository
import okhttp3.ResponseBody
import retrofit2.Callback


class ProductViewModel: ViewModel() {
    private val productRepository= ProductRepository(RetrofitClient.productService)
    fun get (callback:Callback<List<Product>>){
        productRepository.get(callback)
    }
    fun getImage( name:String, callback: Callback<ResponseBody>) {
        productRepository.getImage(name,callback)
    }
    fun type( type:String, callback: Callback<List<Product>>) {
        productRepository.type(type,callback)
    }
    // returns info of specific product by id
    fun product (id:String, callback: Callback<Product>){
        productRepository.product(id,callback)
    }

    // adds comment to product given by id
    fun comment (comment:CommentData, callback:Callback<String>){
        productRepository.comment(comment,callback)
    }

    // adds new product

    fun add(product:Product, callback:Callback<String>){
        productRepository.add(product,callback)

    }
}