package com.pki.cakeshop.viewmodels
import androidx.lifecycle.ViewModel
import com.pki.cakeshop.RetrofitClient
import com.pki.cakeshop.models.Promotion
import com.pki.cakeshop.repositories.PromotionRepository
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.GET
import retrofit2.http.Path

class PromotionViewModel: ViewModel() {
    private val promotionRepository= PromotionRepository(RetrofitClient.promotionService);
    fun getImage( name:String, callback: Callback<ResponseBody>) {
        promotionRepository.getImage(name,callback)
    }

    fun get( callback: Callback<List<Promotion>>) {
        promotionRepository.get(callback)
    }
}