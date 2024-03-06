package com.pki.cakeshop
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
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
import com.pki.cakeshop.viewmodels.ProductViewModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class ItemAdapter ( private val productsinfo: List<ProductInfo>,private val products: List<Product>, private val productViewModel: ProductViewModel/*, private val images: Map<String,Bitmap>*/) :
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

        fun bind(productsinfo: ProductInfo,product:Product/*,image:Bitmap?*/) {
            /*image?.let { img->
               this.image.setImageBitmap(img)
            }*/
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
            name.setText(product.name)
            quantity.setText(productsinfo.amount.toString())
            price.setText(product.price.toString()+" din")
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
        //val image = images[product._id+"."+product.image]
        viewHolder.bind(productinfo,product/*,image*/)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = productsinfo.size

    }