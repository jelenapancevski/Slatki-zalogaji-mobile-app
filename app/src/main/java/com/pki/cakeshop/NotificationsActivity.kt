package com.pki.cakeshop

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Adapter
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.pki.cakeshop.models.Order
import com.pki.cakeshop.models.Product
import com.pki.cakeshop.models.ProductInfo
import com.pki.cakeshop.models.User
import com.pki.cakeshop.viewmodels.OrderViewModel
import com.pki.cakeshop.viewmodels.ProductViewModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NotificationsActivity : AppCompatActivity() {
    private lateinit var notificationsContainer: LinearLayout
    private lateinit var orderViewModel: OrderViewModel
    private lateinit var notifications: List<Order>
    private lateinit var  productViewModel:ProductViewModel
    private lateinit var products:List<Product>
    private lateinit var user:User
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notifications)
        orderViewModel = OrderViewModel()
        productViewModel = ProductViewModel()
        val pref = getSharedPreferences("data", Context.MODE_PRIVATE)
        user = Gson().fromJson(pref.getString("user",null), User::class.java)
        if(user==null){
            //error
        }
        productViewModel.get(object : Callback<List<Product>> {
            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                if (response.isSuccessful) {
                    products = response.body()!!

                    orderViewModel.notifications(user._id,object:Callback<List<Order>>{
                        override fun onResponse(call: Call<List<Order>>, response: Response<List<Order>>) {
                            if (response.isSuccessful) {
                                notifications = response.body()!!
                                Log.e("NOTIFIKACIJE",notifications.toString())

                                notificationsContainer = findViewById<LinearLayout>(R.id.notifications)

                                for (notification in notifications) {
                                    val notificationView = layoutInflater.inflate(R.layout.notification,null)
                                    val status = notificationView.findViewById<TextView>(R.id.status)
                                    val price = notificationView.findViewById<TextView>(R.id.price)
                                    val recyclerView =
                                        notificationView.findViewById<RecyclerView>(R.id.recycler_view)

                                    // Set status and total amount for the notification
                                    if (notification.status == "accepted") {
                                        status.text =
                                            "Vaša porudžbina kreirana " + formatDate(notification.date) + " je prihvaćena."

                                    } else if (notification.status == "denied") {
                                        status.text =
                                            "Vaša porudžbina kreirana " + formatDate(notification.date) + " je odbijena."
                                    }



                                    var notificationProducts: List<Product> = getProducts(notification.products)

                                    price.text = "Total Amount: "+ calculatePrice(notificationProducts,notification.products)+" rsd"

                                    // Set up RecyclerView for products
                                    Log.e("InfoProdukti", notification.products.toString())
                                    Log.e("Produkti", notificationProducts.toString())
                                    Log.e("ProductViewModel",productViewModel.toString())

                                    recyclerView.layoutManager = LinearLayoutManager(notificationView.context)
                                    recyclerView.adapter = ItemAdapter(notification.products,notificationProducts,productViewModel)
                                    notificationsContainer.addView(notificationView)
                                }

                            } else {
                                // Handle the case where the request was not successful
                                Log.e("NotificationsActivity", "Request failed: ${response.code()}")
                                //Log.e("ERRROR", "" + call.req
                            }
                        }

                        override fun onFailure(call: Call<List<Order>>, t: Throwable) {
                            Log.e("NotificationsActivity", "Request failed: ${t.stackTrace}")
                        }


                    })

                }
            }

            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                Log.e("NotificationsActivity", "Request failed: ${t.stackTrace}")
            }

        })



    }

    private fun calculatePrice(products: List<Product>, productsinfo: List<ProductInfo>): Any? {
        var index=0
        var totalPrice=0
        products.forEach { product->
            totalPrice+= product.price*productsinfo[index].amount
            index++
        }
        return totalPrice
    }

    private fun getProducts(productsInfo:List<ProductInfo>):MutableList<Product>{
        val product_list:MutableList<Product> = mutableListOf()
        productsInfo.forEach { pi->
            products?.forEach{ product->
                if(product._id==pi.productid){
                    product_list.add(product)
                }
            }
        }
        return product_list
    }
    fun formatDate(date: Date): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return sdf.format(date)
    }


}