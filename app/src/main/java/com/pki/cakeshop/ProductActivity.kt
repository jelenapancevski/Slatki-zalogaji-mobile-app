package com.pki.cakeshop
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.OnBackPressedDispatcherOwner
import com.google.gson.Gson
import com.pki.cakeshop.models.Product
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.pki.cakeshop.models.Comment
import com.pki.cakeshop.models.CommentData
import com.pki.cakeshop.models.Order
import com.pki.cakeshop.models.ProductInfo
import com.pki.cakeshop.models.User
import com.pki.cakeshop.viewmodels.ProductViewModel
import com.pki.cakeshop.viewmodels.UserViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Date

class ProductActivity : MenuActivity(), OnBackPressedDispatcherOwner {
    private lateinit var  userViewModel:UserViewModel
    private lateinit var productViewModel: ProductViewModel
    private lateinit var product: Product
    private lateinit var image:Bitmap
    private lateinit var commentsView: RecyclerView
    private lateinit var commentAdapter: CommentAdapter
    private lateinit var newComment: TextInputEditText
    private lateinit var user:User
    private lateinit var amount:TextInputEditText
    private lateinit var order:Order
    private val backCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            // Handle back button press here
            // Update the product and send it to the ProductsActivity
            val returnIntent = Intent()
            returnIntent.putExtra("updatedProduct", Gson().toJson(product))
            setResult(Activity.RESULT_OK, returnIntent)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.product)
        onBackPressedDispatcher.addCallback(this, backCallback)
        userViewModel = UserViewModel()
        productViewModel = ProductViewModel()
        val pref = getSharedPreferences("data", Context.MODE_PRIVATE)
        product = Gson().fromJson(pref.getString("product",null),Product::class.java)
        image = Gson().fromJson(pref.getString("product_image",null),Bitmap::class.java)

        if(pref.getString("user",null)==null){
            //error
            Log.e("ProductActivity", "Error user is null")
            return
        }
        user = Gson().fromJson(pref.getString("user",null),User::class.java)
        commentsView = findViewById(R.id.comments)
        commentsView.layoutManager=LinearLayoutManager(this)
        findViewById<TextView>(R.id.name).text=product.name
        findViewById<TextView>(R.id.description).text=product.description
        findViewById<TextView>(R.id.price).text= getString(R.string.product_price,product.price.toString())
        findViewById<ImageView>(R.id.image).setImageBitmap(image)
        var ingredients=""
        product.ingridients.forEach { ingredient->
            ingredients+=ingredient+"\n"
        }
        findViewById<TextView>(R.id.ingredients).text=ingredients
        product.comments = product.comments.sortedByDescending { it.date }.toMutableList()
        commentAdapter = CommentAdapter(product.comments,userViewModel)
        commentsView.adapter=commentAdapter

        amount = findViewById(R.id.amount)
        findViewById<Button>(R.id.addtobasket).setOnClickListener{
            if(amount.text.isNullOrBlank()){
                return@setOnClickListener

            }
            if (Integer.parseInt(amount.text.toString())==0){
                amount.text=null
                return@setOnClickListener
            }
            if(pref.getString("order",null)==null){
                order = Order(user._id, mutableListOf(),Date(),"pending",null)
            }
            else order = Gson().fromJson(pref.getString("order",null),Order::class.java)

            var found = false
            this.order.products.forEach { prod ->
                if (prod.productid == this.product._id) {
                    prod.amount += Integer.parseInt(amount.text.toString())
                    found = true
                }
            }
            if(!found){
                order.products.add(ProductInfo(product._id,Integer.parseInt(amount.text.toString())))            }

            amount.clearFocus()
            amount.text=null
            findViewById<TextView>(R.id.message).text = getString(R.string.added_to_basket)
            //save updated order
            val editor = pref.edit()
            editor.putString("order", Gson().toJson(order))
            editor.apply()
        }

        newComment = findViewById(R.id.newcomment)
        findViewById<Button>(R.id.addcomment).setOnClickListener{
                if(newComment.text.isNullOrBlank()){
                    return@setOnClickListener
                }
            // else add new comment
            val comment = Comment(user._id,newComment.text.toString(), Date())
            productViewModel.comment(CommentData(comment,product._id),object:Callback<String>{
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if(response.isSuccessful && response.body().equals("Added comment")){
                        newComment.setText("")
                        newComment.clearFocus()

                        product.comments.add(0,comment)
                        commentAdapter.notifyItemInserted(0)

                        commentsView.scrollToPosition(0)
                        if(commentsView.getChildAt(0)!=null)
                            findViewById<ScrollView>(R.id.scroll).scrollTo(0,commentsView.getChildAt(0).top )
                        hideKeyboard()

                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
        }
    }

    override fun onResume() {
        super.onResume()
        findViewById<TextView>(R.id.message).text = null
        newComment.text = null

    }
    private fun hideKeyboard() {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }
}