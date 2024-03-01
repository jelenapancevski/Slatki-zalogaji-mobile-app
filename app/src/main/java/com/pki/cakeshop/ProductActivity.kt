package com.pki.cakeshop
import android.content.Context
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.google.gson.Gson
import com.pki.cakeshop.models.Product
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pki.cakeshop.models.User
import com.pki.cakeshop.viewmodels.UserViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class ProductActivity : AppCompatActivity() {
    private lateinit var  userViewModel:UserViewModel
    private lateinit var product: Product
    private lateinit var image:Bitmap
    private var usernames: MutableList<String> = mutableListOf()
    private lateinit var commentsView: RecyclerView
    private lateinit var commentAdapter: CommentAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.product)
        userViewModel = UserViewModel()
        val pref = getSharedPreferences("data", Context.MODE_PRIVATE)
        product = Gson().fromJson(pref.getString("product",null),Product::class.java)
        image = Gson().fromJson(pref.getString("product_image",null),Bitmap::class.java)
        commentsView = findViewById(R.id.comments);
        commentsView.setLayoutManager(LinearLayoutManager(this));
        findViewById<TextView>(R.id.name).text=product.name
        findViewById<TextView>(R.id.description).text=product.description
        findViewById<TextView>(R.id.price).text="Cena "+product.price+" din"
        findViewById<ImageView>(R.id.image).setImageBitmap(image)
        var ingredients:String=""
        product.ingridients.forEach { ingredient->
            ingredients+=ingredient+"\n"
        }
        findViewById<TextView>(R.id.ingredients).text=ingredients
        product.comments.forEach { comment->
            userViewModel.user(comment.username,object: Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if(response.isSuccessful){
                        usernames.add(response.body()?.username!!)
                       // commentAdapter.notifyDataSetChanged()
                    }
                }
                override fun onFailure(call: Call<User>, t: Throwable) {
                    Log.e("ProductActivity","Couldn't find user with _id="+comment.username)
                }

            })
        }
        commentAdapter = CommentAdapter(product.comments,usernames)
        commentsView.adapter=commentAdapter
    }
}