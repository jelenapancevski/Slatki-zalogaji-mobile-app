package com.pki.cakeshop
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pki.cakeshop.models.Product
import com.pki.cakeshop.viewmodels.ProductViewModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class ProductsActivity : MenuActivity() {
    private lateinit var type:String
    private lateinit var productViewModel: ProductViewModel
    private lateinit var products: List<Product>
    private lateinit var images: MutableMap<String,Bitmap>
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.allproducts)
        type = intent.getStringExtra("type").toString()
        if(type.equals("torta")){
            findViewById<TextView>(R.id.title).text="Torte"
        }
        else {
            findViewById<TextView>(R.id.title).text="Kolači"
        }
        productViewModel = ProductViewModel()
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(LinearLayoutManager(this));
        images = mutableMapOf()
        productViewModel.type(type,object : Callback<List<Product>> {
            override fun onResponse(
                call: Call<List<Product>>,
                response: Response<List<Product>>
            ) {
                if (response.isSuccessful) {
                    products = response.body()!!
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

    /*
    val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val updatedProductJson = data?.getStringExtra("updatedProduct")
                val updatedProduct = Gson().fromJson(updatedProductJson, Product::class.java)
                cakes.forEach { cake->
                    if(cake._id==updatedProduct._id){
                       cake.comments = updatedProduct.comments
                    }
                }
                // Update the list of products with the updated product
            }
        }

    */

}