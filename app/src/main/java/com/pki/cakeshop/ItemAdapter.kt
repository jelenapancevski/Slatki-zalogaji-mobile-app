package com.pki.cakeshop
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.pki.cakeshop.models.Product
import com.pki.cakeshop.models.ProductInfo

class ItemAdapter ( private val productsinfo: List<ProductInfo>,private val products: List<Product>, private val images: Map<String,Bitmap>) :
    RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {
   inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
       val image: ImageView
        val name: TextView
       val quantity: TextView
       val price: TextView
       //val deletebutton: TextView
        init {
            // Define click listener for the ViewHolder's View
           image = view.findViewById(R.id.image)
           name = view.findViewById(R.id.name)
           quantity = view.findViewById(R.id.quantity)
           price = view.findViewById(R.id.price)
        }

        fun bind(productsinfo: ProductInfo,product:Product,image:Bitmap?) {
            image?.let { image->
               this.image.setImageBitmap(image)
            }
            name.setText(product.name)
            quantity.setText(productsinfo.amount)
            price.setText(product.price)
        }
    }
    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item, viewGroup, false)
        return ItemViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ItemViewHolder, position: Int) {
        val productinfo = productsinfo[position]
        val product = products[position]
        val image = images[product._id+"."+product.image]
        viewHolder.bind(productinfo,product,image)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = productsinfo.size

    }