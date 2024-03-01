package com.pki.cakeshop

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pki.cakeshop.models.Order
import com.pki.cakeshop.models.Product
import com.pki.cakeshop.viewmodels.OrderViewModel
import com.pki.cakeshop.viewmodels.ProductViewModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class NotificationsActivity : AppCompatActivity() {
   /* override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notifications)
        orderViewModel = OrderViewModel()
        recyclerView = findViewById(R.id.notifications);
        recyclerView.setLayoutManager(LinearLayoutManager(this));
        orderViewModel.notifications("",object:Callback<List<Order>>{
            override fun onResponse(call: Call<List<Order>>, response: Response<List<Order>>) {
                if (response.isSuccessful) {
                    notifications = response.body()!!

                    getImages()
                    adapter = ProductAdapter(this@NotificationsActivity,cakes,images)
                    recyclerView.adapter=adapter

                } else {
                    // Handle the case where the request was not successful
                    Log.e("NotificationsActivity", "Request failed: ${response.code()}")
                    //Log.e("ERRROR", "" + call.req
            }

            override fun onFailure(call: Call<List<Order>>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
        }
    }
    private lateinit var orderViewModel: OrderViewModel
    private lateinit var notifications: List<Order>
    //private lateinit var images: MutableMap<String, Bitmap>
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NotificationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.allproducts)




        images = mutableMapOf()
        productViewModel.type("torta",object : Callback<List<Product>> {
            override fun onResponse(
                call: Call<List<Product>>,
                response: Response<List<Product>>
            ) {
                if (response.isSuccessful) {
                    cakes = response.body()!!
                    //Log.e("CAKES FOUND",cakes.toString())
                    getImages()
                    adapter = ProductAdapter(this@CakesActivity,cakes,images)
                    recyclerView.adapter=adapter

                } else {
                    // Handle the case where the request was not successful
                    Log.e("CakesActivity", "Request failed: ${response.code()}")
                    //Log.e("ERRROR", "" + call.request().body)
                }
            }
            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                Log.e("CakesActivity", "Request failed: ${t.message}")
            }
        })
    }

    private fun getImages(){
        cakes.forEach { cake ->
            productViewModel.getImage(cake._id+"."+cake.image, object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if(response.isSuccessful && response.body()!=null && response.body()!!.contentLength()>0) {
                        try {
                            val byteArray = response.body()?.bytes()
                            if (byteArray != null) {
                                val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
                                images[cake._id + "." + cake.image] = bitmap
                                adapter.notifyDataSetChanged()
                            }
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                    }               }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.e("CakesActivity", "Request failed: ${t.message}")
                }

            })

        }

    }*/
}