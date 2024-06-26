package com.pki.cakeshop

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pki.cakeshop.models.Promotion
import com.pki.cakeshop.viewmodels.PromotionViewModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException


class PromotionAdapter (private val promotions: List<Promotion>, private val layout:Int) :
    RecyclerView.Adapter<PromotionAdapter.PromotionViewHolder>() {
    class PromotionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val title: TextView
        val image: ImageView
        val text: TextView

        init {
            title = view.findViewById(R.id.promotion_title)
            image = view.findViewById(R.id.promotion_image)
            text = view.findViewById(R.id.promotion_text)
        }

        fun bind(promotion: Promotion) {
                val promotionViewModel = PromotionViewModel()
                promotionViewModel.getImage(promotion._id+"."+promotion.image, object :
                    Callback<ResponseBody> {
                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        if (response.isSuccessful && response.body() != null && response.body()!!.contentLength() > 0) {
                            try {
                                val byteArray = response.body()?.bytes()
                                if (byteArray != null) {
                                    val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
                                    image.setImageBitmap(bitmap)
                                }
                            } catch (e: IOException) {
                                e.printStackTrace()
                            }
                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        // Handle failure
                    }
                })
            title.text = promotion.name
            text.text = promotion.description
        }
    }
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): PromotionViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(layout, viewGroup, false)
        return PromotionViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: PromotionViewHolder, position: Int) {
        val promotion = promotions[position]
        viewHolder.bind(promotion)
    }

    override fun getItemCount() = promotions.size

    }