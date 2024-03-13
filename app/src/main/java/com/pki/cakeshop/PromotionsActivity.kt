package com.pki.cakeshop

import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pki.cakeshop.models.Promotion
import com.pki.cakeshop.viewmodels.PromotionViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PromotionsActivity : MenuActivity() {
    private lateinit var promotionViewModel: PromotionViewModel
    private lateinit var promotions: List<Promotion>
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PromotionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.promotions)
        promotionViewModel = PromotionViewModel()
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(LinearLayoutManager(this));

        promotionViewModel.get(object : Callback<List<Promotion>> {
            override fun onResponse(
                call: Call<List<Promotion>>,
                response: Response<List<Promotion>>
            ) {
                if (response.isSuccessful) {
                    promotions = response.body()!!
                    adapter = PromotionAdapter(promotions,R.layout.promotion);
                    recyclerView.adapter=adapter

                } else {
                    // Handle the case where the request was not successful
                    Log.e("PromotionActivity", "Request failed: ${response.code()}")
                    Log.e("ERRROR", "" + call.request().body)
                }
            }
            override fun onFailure(call: Call<List<Promotion>>, t: Throwable) {
                Log.e("PromotionActivity", "Request failed: ${t.message}")
            }
        })
    }

}