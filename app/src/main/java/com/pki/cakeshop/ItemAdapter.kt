package com.pki.cakeshop

import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.pki.cakeshop.models.Product
import com.pki.cakeshop.models.ProductInfo
import com.pki.cakeshop.viewmodels.ProductViewModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class ItemAdapter (private val productsInfo: MutableList<ProductInfo>, private val products: MutableList<Product>, private val productViewModel: ProductViewModel, private val isBasket:Boolean, private val onItemClick: ((Int) -> Unit)? = null) :
    RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {
   inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
       val image: ImageView
       val name: TextView
       private val quantity: TextView
       private val price: TextView
       private val deleteButton: ImageView

        init {
            // Define click listener for the ViewHolder's View
           image = view.findViewById(R.id.image)
           name = view.findViewById(R.id.name)
           quantity = view.findViewById(R.id.quantity)
           price = view.findViewById(R.id.price)
            deleteButton = view.findViewById(R.id.deletebutton)
        }

        fun bind(productInfo: ProductInfo, product:Product) {
            productViewModel.getImage(
                product._id + "." + product.image,
                object : Callback<ResponseBody> {
                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        if (response.isSuccessful && response.body() != null && response.body()!!
                                .contentLength() > 0
                        ) {
                            try {
                                val byteArray = response.body()?.bytes()
                                if (byteArray != null) {
                                    val bitmap =
                                        BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
                                    if (image.drawable != null) {
                                        (image.drawable as BitmapDrawable).bitmap.recycle() // Recycle previous bitmap
                                    }
                                    image.setImageBitmap(bitmap)
                                }
                            } catch (e: IOException) {
                                e.printStackTrace()
                            }
                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Log.e("NotificationsActivity", "Request failed: ${t.message}")
                    }

                })
            name.text = product.name
            quantity.text = productInfo.amount.toString()
            price.text = "${product.price} rsd"
            if(isBasket){
                deleteButton.visibility = View.VISIBLE
                deleteButton.setOnClickListener{
                    // delete from order
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        products.removeAt(position)
                        productsInfo.removeAt(position)
                        onItemClick?.let { it1 -> it1(position) }
                        notifyItemRemoved(position)
                    }

                }
            }
            else {
                deleteButton.visibility = View.INVISIBLE
            }
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
        val productInfo = productsInfo[position]
        val product = products[position]
        viewHolder.bind(productInfo,product)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = productsInfo.size

    }