package com.pki.cakeshop
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.pki.cakeshop.models.Product
import com.pki.cakeshop.viewmodels.ProductViewModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class ProductsActivity : MenuActivity(){
    private lateinit var type:String
    private lateinit var productViewModel: ProductViewModel
    private lateinit var products: MutableList<Product>
    private lateinit var images: MutableMap<String,Bitmap>
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProductAdapter
    val  REQUEST_CODE_UPDATE_PRODUCT=1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.allproducts)

        type = intent.getStringExtra("type").toString()
        if(type == "torta"){
            findViewById<TextView>(R.id.title).text = getString(R.string.cakes)
        }
        else {
            findViewById<TextView>(R.id.title).text = getString(R.string.desserts)
        }
        productViewModel = ProductViewModel()
        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        images = mutableMapOf()
        productViewModel.type(type,object : Callback<List<Product>> {
            override fun onResponse(
                call: Call<List<Product>>,
                response: Response<List<Product>>
            ) {
                if (response.isSuccessful) {
                    products = (response.body() as MutableList<Product>?)!!
                    getImages()
                    adapter = ProductAdapter(this@ProductsActivity,products,images)
                    recyclerView.adapter=adapter

                } else {
                    // Handle the case where the request was not successful
                    Log.e("ProductsActivity", "Request failed: ${response.code()}")
                }
            }
            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                Log.e("ProductsActivity", "Request failed: ${t.message}")
            }
        })
    }

    private fun getImages(){
        products.forEach { product ->
            productViewModel.getImage(product._id+"."+product.image, object :Callback<ResponseBody>{
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                if(response.isSuccessful && response.body()!=null && response.body()!!.contentLength()>0) {
                    try {
                        val byteArray = response.body()?.bytes()
                        if (byteArray != null) {
                            val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
                            images[product._id + "." + product.image] = bitmap
                            adapter.notifyDataSetChanged()

                        }
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }               }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.e("ProductsActivity", "Request failed: ${t.message}")
                }

            })

        }

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_UPDATE_PRODUCT && resultCode == Activity.RESULT_OK) {
            val updatedProduct = Gson().fromJson(data?.extras?.getString("updatedProduct"),Product::class.java)

            // Update the product in the list
            var index=0
            while(index<products.size){
                if(products[index]._id==updatedProduct._id)break
                else index++
            }
            products[index] = updatedProduct
            adapter.notifyItemChanged(index)


        }
    }


}