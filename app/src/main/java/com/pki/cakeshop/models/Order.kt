package com.pki.cakeshop.models

import java.util.Date

data class OrderData (
    var order: Order
)

data class ProductInfo(
    var productid:String,
    var amount:Int
)
data class Order(
    var buyer: String,
    var products: MutableList<ProductInfo>,
    var date: Date,
    var status:String, // pending, accepted, denied
    var notified: Boolean?
)