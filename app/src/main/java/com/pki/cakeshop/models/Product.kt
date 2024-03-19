package com.pki.cakeshop.models

import java.util.Date

data class Comment (
    var username:String,
    var comment: String,
    var date:Date
)

data class CommentData (
    var comment: Comment,
    var id:String
)

data class Product(
 var _id:String,
 var ingridients: List<String>,
 var name:String,
 var description:String,
 var type: String,
 var image: String,
 var price:Int,
 var comments: MutableList <Comment>
)