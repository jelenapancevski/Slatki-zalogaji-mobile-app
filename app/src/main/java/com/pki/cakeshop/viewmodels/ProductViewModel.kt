package com.pki.cakeshop.viewmodels
import androidx.lifecycle.ViewModel
import com.pki.cakeshop.RetrofitClient
import com.pki.cakeshop.models.Product
import com.pki.cakeshop.repositories.ProductRepository
import okhttp3.ResponseBody
import retrofit2.Callback
class ProductViewModel: ViewModel() {
    private val productRepository= ProductRepository(RetrofitClient.productService);
    fun getImage( name:String, callback: Callback<ResponseBody>) {
        productRepository.getImage(name,callback)
    }
    fun type( type:String, callback: Callback<List<Product>>) {
        productRepository.type(type,callback)
    }
}