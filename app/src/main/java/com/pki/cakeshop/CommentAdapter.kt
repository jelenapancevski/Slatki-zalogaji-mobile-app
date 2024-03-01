package com.pki.cakeshop
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pki.cakeshop.models.Comment
class CommentAdapter (private val comments: List<Comment>, private val usernames:List<String>) :
    RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {
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

        fun bind(comment: Comment,username:String) {
            this.username.setText(username)
            this.dateandtime.setText(comment.date.toString())
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
        val username = usernames[position]
        viewHolder.bind(comment,username)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = comments.size

    }