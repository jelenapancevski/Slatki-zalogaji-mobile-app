package com.pki.cakeshop
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pki.cakeshop.models.Comment
import com.pki.cakeshop.models.User
import com.pki.cakeshop.viewmodels.UserViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CommentAdapter (private val comments: List<Comment>, private val userViewModel:UserViewModel) :
    RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {
    fun formatDate(date: Date): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return sdf.format(date)
    }
   inner class CommentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val username: TextView
        private val dateandtime:TextView
        private val comment:TextView
        init {
            // Define click listener for the ViewHolder's View
            username = view.findViewById(R.id.username)
            dateandtime = view.findViewById(R.id.dateandtime)
            comment = view.findViewById(R.id.comment)
        }

        fun bind(comment: Comment) {
            userViewModel.user(comment.username,object: Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if(response.isSuccessful){
                        username.setText(response.body()?.username!!)
                        // commentAdapter.notifyDataSetChanged()
                    }
                }
                override fun onFailure(call: Call<User>, t: Throwable) {
                    Log.e("CommentAdapter","Couldn't find user with _id="+comment.username)
                }

            })
            //this.username.setText(username)
            this.dateandtime.setText(formatDate(comment.date))
            this.comment.setText(comment.comment)
        }
    }
    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): CommentViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.comment, viewGroup, false)
        return CommentViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: CommentViewHolder, position: Int) {
        val comment = comments[position]
        viewHolder.bind(comment)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = comments.size

    }