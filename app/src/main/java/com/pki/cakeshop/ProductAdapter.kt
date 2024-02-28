package com.pki.cakeshop
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pki.cakeshop.models.Product
class ProductAdapter (private val products: List<Product>, private val images: Map<String,Bitmap>) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView
        val image: ImageView

        init {
            // Define click listener for the ViewHolder's View
            name = view.findViewById(R.id.product_name)
            image = view.findViewById(R.id.product_image)
        }

        fun bind(product: Product,img:Bitmap?) {
            img?.let { img->
                image.setImageBitmap(img)
            }
            name.setText(product.name)
        }
    }
    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.product, viewGroup, false)
        return ProductViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ProductViewHolder, position: Int) {
        val product = products[position]
        val image = images[product._id+"."+product.image]
        viewHolder.bind(product,image)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = products.size

    }