package com.pki.cakeshop.models

import org.bson.types.ObjectId
import java.util.Date

data class Comment (
    var username:String,
    var comment: String,
    var date:Date
)
data class Product(
 var _id:String,
 var name:String,
 var description:String,
 var type: String,
 var image: String,
 var ingridents: List<String>,
 var price:Int,
 var comments: List <Comment>
)