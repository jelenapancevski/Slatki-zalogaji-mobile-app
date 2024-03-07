package com.pki.cakeshop
import android.content.Context
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.google.gson.Gson
import com.pki.cakeshop.models.Product
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.pki.cakeshop.models.Comment
import com.pki.cakeshop.models.CommentData
import com.pki.cakeshop.models.User
import com.pki.cakeshop.viewmodels.ProductViewModel
import com.pki.cakeshop.viewmodels.UserViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Date

class ProductActivity : AppCompatActivity() {
    private lateinit var  userViewModel:UserViewModel
    private lateinit var productViewModel: ProductViewModel
    private lateinit var product: Product
    private lateinit var image:Bitmap
    private lateinit var commentsView: RecyclerView
    private lateinit var commentAdapter: CommentAdapter
    private lateinit var newcomment: TextInputEditText
    private lateinit var user:User
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.product)
        userViewModel = UserViewModel()
        productViewModel = ProductViewModel()
        val pref = getSharedPreferences("data", Context.MODE_PRIVATE)
        product = Gson().fromJson(pref.getString("product",null),Product::class.java)
        image = Gson().fromJson(pref.getString("product_image",null),Bitmap::class.java)
        user = Gson().fromJson(pref.getString("user",null),User::class.java)
        if(user==null){
            //error
        }
        commentsView = findViewById(R.id.comments);
        commentsView.setLayoutManager(LinearLayoutManager(this));
        findViewById<TextView>(R.id.name).text=product.name
        findViewById<TextView>(R.id.description).text=product.description
        findViewById<TextView>(R.id.price).text="Cena "+product.price.toString()+" din"
        findViewById<ImageView>(R.id.image).setImageBitmap(image)
        var ingredients:String=""
        product.ingridients.forEach { ingredient->
            ingredients+=ingredient+"\n"
        }
        findViewById<TextView>(R.id.ingredients).text=ingredients
        commentAdapter = CommentAdapter(product.comments,userViewModel)
        commentsView.adapter=commentAdapter

        newcomment = findViewById(R.id.newcomment)
        findViewById<Button>(R.id.addcomment).setOnClickListener{
                if(newcomment.text.isNullOrBlank()){
                    return@setOnClickListener
                }
            // else add new comment
            val comment : Comment = Comment(user._id,newcomment.text.toString(), Date())
            productViewModel.comment(CommentData(comment,product._id),object:Callback<String>{
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    TODO("Not yet implemented")
                    //reload comments
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
        }
    }
}