package com.pki.cakeshop.repositories

import com.pki.cakeshop.models.Promotion
import com.pki.cakeshop.services.PromotionService
import okhttp3.ResponseBody
import retrofit2.Callback

class PromotionRepository(private val promotionService:PromotionService) {
    fun get(callback: Callback<List<Promotion>>) {
        promotionService.get().enqueue(callback)
    }
    fun getImage( name:String, callback: Callback<ResponseBody>) {
        promotionService.getImage(name).enqueue(callback)
    }
}