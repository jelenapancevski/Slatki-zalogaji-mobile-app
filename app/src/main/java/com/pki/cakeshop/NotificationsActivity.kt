package com.pki.cakeshop

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.ViewFlipper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.pki.cakeshop.models.Order
import com.pki.cakeshop.models.Product
import com.pki.cakeshop.models.ProductInfo
import com.pki.cakeshop.models.User
import com.pki.cakeshop.viewmodels.OrderViewModel
import com.pki.cakeshop.viewmodels.ProductViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NotificationsActivity : MenuActivity() {
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
        if(pref.getString("user",null)==null){
            //error
            Log.e("NotificationsActivity", "Error user is null")
            return
        }
        user = Gson().fromJson(pref.getString("user",null), User::class.java)

        productViewModel.get(object : Callback<List<Product>> {
            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                if (response.isSuccessful) {
                    products = response.body()!!

                    orderViewModel.notifications(user._id,object:Callback<List<Order>>{
                        override fun onResponse(call: Call<List<Order>>, response: Response<List<Order>>) {
                            if (response.isSuccessful) {
                                notifications = response.body()!!
                                notifications = notifications.sortedByDescending { it.date }

                                notificationsContainer = findViewById(R.id.notifications)

                                val viewFlipper = findViewById<ViewFlipper>(R.id.viewFlipper)
                                if(notifications.isNotEmpty()){
                                    viewFlipper.displayedChild = 1
                                    findViewById<TextView>(R.id.empty_notifications).visibility = View.GONE
                                }
                                else {
                                    viewFlipper.displayedChild = 0
                                    findViewById<TextView>(R.id.empty_notifications).text = getString(R.string.no_new_notifications)
                                    notificationsContainer.visibility = View.GONE

                                }
                                for (notification in notifications) {
                                    val notificationView = layoutInflater.inflate(R.layout.notification,null)
                                    notificationView.findViewById<TextView>(R.id.buyer).visibility = View.GONE
                                    notificationView.findViewById<TextView>(R.id.address).visibility = View.GONE
                                    notificationView.findViewById<TextView>(R.id.phone).visibility = View.GONE
                                    notificationView.findViewById<Button>(R.id.purchasebutton).visibility = View.GONE
                                    notificationView.findViewById<TextView>(R.id.delivery).visibility = View.GONE

                                    val status = notificationView.findViewById<TextView>(R.id.status)
                                    val price = notificationView.findViewById<TextView>(R.id.price)
                                    val recyclerView =
                                        notificationView.findViewById<RecyclerView>(R.id.recycler_view)

                                    // Set status and total amount for the notification
                                    if (notification.status == "accepted") {
                                        status.text = getString(R.string.status_accepted,formatDate(notification.date))

                                    } else if (notification.status == "denied") {
                                        status.text = getString(R.string.status_denied,formatDate(notification.date))
                                    }



                                    val notificationProducts: MutableList<Product> = getProducts(notification.products)

                                    price.text = getString(R.string.total_price,calculatePrice(notificationProducts,notification.products).toString())


                                    recyclerView.layoutManager = LinearLayoutManager(notificationView.context)
                                    recyclerView.adapter = ItemAdapter(notification.products,notificationProducts,productViewModel,false)
                                    notificationsContainer.addView(notificationView)
                                }

                            } else {
                                // Handle the case where the request was not successful
                                Log.e("NotificationsActivity", "Request failed: ${response.code()}")
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

    private fun calculatePrice(products: List<Product>, productsInfo: List<ProductInfo>): Int {
        var index=0
        var totalPrice=0
        products.forEach { product->
            totalPrice+= product.price*productsInfo[index].amount
            index++
        }
        return totalPrice
    }

    private fun getProducts(productsInfo:List<ProductInfo>):MutableList<Product>{
        val productList:MutableList<Product> = mutableListOf()
        productsInfo.forEach { pi->
            products.forEach{ product->
                if(product._id==pi.productid){
                    productList.add(product)
                }
            }
        }
        return productList
    }
    fun formatDate(date: Date): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return sdf.format(date)
    }


}