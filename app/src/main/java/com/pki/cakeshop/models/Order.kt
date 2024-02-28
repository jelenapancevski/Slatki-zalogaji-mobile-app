package com.pki.cakeshop.models

import org.bson.types.ObjectId
import java.util.Date


data class ProductInfo(
    var productid:String,
    var amount:Int
);
data class Order (
   var buyer: String,
   var products: List<ProductInfo>,
   var date: Date,
   var status:String, // pending, accepted, denied
   var notified: Boolean
);