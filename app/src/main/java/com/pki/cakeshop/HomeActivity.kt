package com.pki.cakeshop

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.helper.widget.Carousel
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.pki.cakeshop.models.Product
import com.pki.cakeshop.models.Promotion
import retrofit2.Call
import com.pki.cakeshop.viewmodels.PromotionViewModel
import retrofit2.Response
import retrofit2.Callback
import java.util.Timer
import java.util.TimerTask

class HomeActivity : MenuActivity() {
    private val promotionViewModel:PromotionViewModel = PromotionViewModel()
    private lateinit var promotions:List<Promotion>
    private lateinit var promotionsAdapter:PromotionAdapter
    private lateinit var viewPager: ViewPager2
    private var currentPage = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)
        viewPager = findViewById(R.id.viewPager)

        promotionViewModel.get(object:Callback<List<Promotion>>{
            override fun onResponse(
                call: Call<List<Promotion>>,
                response: Response<List<Promotion>>
            ) {
                if(response.isSuccessful){
                    promotions = response.body()!!
                    promotionsAdapter = PromotionAdapter(promotions,R.layout.home_promotion)
                    viewPager.adapter = promotionsAdapter
                    // Auto-scroll ViewPager2
                    val handler = Handler(Looper.getMainLooper())
                    val update = Runnable {
                        if (currentPage == promotions.size) {
                            currentPage = 0
                        }
                        viewPager.setCurrentItem(currentPage++, true)
                    }
                    Timer().schedule(object : TimerTask() {
                        override fun run() {
                            handler.post(update)
                        }
                    }, 3000, 3000) // Auto scroll every 3 seconds

                    // Swipe listener
                    viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                        override fun onPageSelected(position: Int) {
                            super.onPageSelected(position)
                            currentPage = position
                        }
                    })
                }

            }
            override fun onFailure(call: Call<List<Promotion>>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })




    }

    }
