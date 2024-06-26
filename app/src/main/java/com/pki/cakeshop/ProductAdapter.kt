package com.pki.cakeshop
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.pki.cakeshop.models.Product
class ProductAdapter ( private val context: Context, private val products: List<Product>, private val images: Map<String,Bitmap>) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
   inner class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val name: TextView
        private val image: ImageView
        private val  REQUEST_CODE_UPDATE_PRODUCT=1


       init {
            name = view.findViewById(R.id.product_name)
            image = view.findViewById(R.id.product_image)
        }

        fun bind(product: Product,img:Bitmap?) {
            img?.let { img->
                image.setImageBitmap(img)
            }
            name.text = product.name

            image.setOnClickListener{
                val pref = context.getSharedPreferences("data", Context.MODE_PRIVATE)
                val editor = pref.edit()
                editor.putString("product", Gson().toJson(product))
                editor.putString("product_image",Gson().toJson(img))
                editor.apply()
                val intent = Intent(context, ProductActivity::class.java)
                (context as Activity).startActivityForResult(intent, REQUEST_CODE_UPDATE_PRODUCT)
            }
            name.setOnClickListener{
                val pref = context.getSharedPreferences("data", Context.MODE_PRIVATE)
                val editor = pref.edit()
                editor.putString("product", Gson().toJson(product))
                editor.putString("product_image",Gson().toJson(img))
                editor.apply()
                val intent = Intent(context, ProductActivity::class.java)
                (context as Activity).startActivityForResult(intent, REQUEST_CODE_UPDATE_PRODUCT)
            }
        }
    }
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.products, viewGroup, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ProductViewHolder, position: Int) {
        val product = products[position]
        val image = images[product._id+"."+product.image]
        viewHolder.bind(product,image)
    }

    override fun getItemCount() = products.size

    }