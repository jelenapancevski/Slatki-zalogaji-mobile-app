package com.pki.cakeshop

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pki.cakeshop.models.Product
import com.pki.cakeshop.viewmodels.ProductViewModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class DessertsActivity : AppCompatActivity() {
    private lateinit var productViewModel: ProductViewModel
    private lateinit var desserts: List<Product>
    private lateinit var images: MutableMap<String,Bitmap>
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.desserts)
        productViewModel = ProductViewModel()
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(LinearLayoutManager(this));
        images = mutableMapOf()
        productViewModel.type("kolač",object : Callback<List<Product>> {
            override fun onResponse(
                call: Call<List<Product>>,
                response: Response<List<Product>>
            ) {
                if (response.isSuccessful) {
                    desserts = response.body()!!
                    getImages()
                    adapter = ProductAdapter(desserts,images)
                    recyclerView.adapter=adapter

                } else {
                    // Handle the case where the request was not successful
                    Log.e("CakesActivity", "Request failed: ${response.code()}")
                    Log.e("ERRROR", "" + call.request().body)
                }
            }
            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                Log.e("DessertsActivity", "Request failed: ${t.message}")
            }
        })
    }

    private fun getImages(){
        desserts.forEach { dessert ->
            productViewModel.getImage(dessert._id+"."+dessert.image, object :Callback<ResponseBody>{
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                if(response.isSuccessful && response.body()!=null && response.body()!!.contentLength()>0) {
                    try {
                        val byteArray = response.body()?.bytes()
                        if (byteArray != null) {
                            val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
                            images[dessert._id + "." + dessert.image] = bitmap
                            adapter.notifyDataSetChanged()
                        }
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }               }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.e("DessertsActivity", "Request failed: ${t.message}")
                }

            })

        }

    }

}