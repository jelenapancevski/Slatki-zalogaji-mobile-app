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
import com.pki.cakeshop.models.OrderData
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

class BasketActivity : MenuActivity() {
    private lateinit var user:User
    private lateinit var order:Order
    private lateinit var products:List<Product>
    private val productViewModel: ProductViewModel = ProductViewModel()
    private val orderViewModel: OrderViewModel = OrderViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basket)

        val pref = getSharedPreferences("data", Context.MODE_PRIVATE)
        user = Gson().fromJson(pref.getString("user",null),User::class.java)

        if(pref.getString("order",null)==null){
            order = Order(user._id, mutableListOf(), Date(),"pending",false)
        }
        else order = Gson().fromJson(pref.getString("order",null), Order::class.java)

        val viewFlipper = findViewById<ViewFlipper>(R.id.viewFlipper)

        if(order.products.size==0) {
            viewFlipper.displayedChild = 0
            findViewById<TextView>(R.id.empty_basket).text=getString(R.string.empty_basket_message)
        }
        else {
            viewFlipper.displayedChild = 1
            findViewById<TextView>(R.id.empty_basket).visibility=View.INVISIBLE
            productViewModel.get(object : Callback<List<Product>> {
                override fun onResponse(
                    call: Call<List<Product>>,
                    response: Response<List<Product>>
                ) {
                    if (response.isSuccessful) {
                        products = response.body()!!
                        // set basket view
                        val basket = findViewById<LinearLayout>(R.id.basket)
                        val basketView = layoutInflater.inflate(R.layout.notification, null)
                        val status = basketView.findViewById<TextView>(R.id.status)
                        val price = basketView.findViewById<TextView>(R.id.price)
                        val recyclerView =
                            basketView.findViewById<RecyclerView>(R.id.recycler_view)

                        // set status/header and total amount of basket
                        status.text = getString(R.string.purchase_date, formatDate(order.date))

                        val orderProducts: MutableList<Product> = getProducts(order.products)

                        price.text = getString(R.string.total_price, calculatePrice(orderProducts, order.products).toString())
                        basketView.findViewById<TextView>(R.id.buyer).text = getString(R.string.buyer,user.firstname,user.lastname)
                        basketView.findViewById<TextView>(R.id.address).text = getString(R.string.buyer_address,user.address.street,user.address.number.toString(),user.address.city)
                        basketView.findViewById<TextView>(R.id.phone).text = getString(R.string.buyer_phone,user.phone)

                        // Set up RecyclerView for products

                        recyclerView.layoutManager = LinearLayoutManager(basketView.context)
                        recyclerView.adapter = ItemAdapter(order.products, orderProducts, productViewModel,true){
                            position->
                            val pref = getSharedPreferences("data", Context.MODE_PRIVATE)
                            val editor = pref.edit()
                            editor.putString("order", Gson().toJson(order))
                            editor.apply()
                            if(order.products.size>0)
                            price.text =
                                getString(R.string.total_price, calculatePrice(orderProducts, order.products).toString())
                            else {
                                viewFlipper.displayedChild = 0
                                findViewById<TextView>(R.id.empty_basket).text=getString(R.string.empty_basket_message)
                            }

                        }

                        basket.addView(basketView)

                        basketView.findViewById<Button>(R.id.purchasebutton).setOnClickListener{
                            // send order
                            // basket is empty
                            order.date = Date()
                            orderViewModel.add(OrderData(order),object:Callback<String>{
                                override fun onResponse(
                                    call: Call<String>,
                                    response: Response<String>
                                ) {
                                    if(response.isSuccessful){
                                        Log.i("BasketActivity",response.body().toString())
                                        // remove order
                                        val editor = pref.edit()
                                        editor.remove("order")
                                        editor.apply()
                                        viewFlipper.displayedChild = 0
                                    }
                                    else Log.e("BasketActivity","Failed to send order to the database")
                                }

                                override fun onFailure(call: Call<String>, t: Throwable) {
                                    Log.e("BasketActivity","Failed to send order to the database")
                                }

                            })
                        }
                    }
                }

                override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                    Log.e("BasketActivity", "Request failed: ${t.stackTrace}")
                }

            })
        }

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