package com.pki.cakeshop.models

data class User(
    var _id:String,
    var username: String?,
    var password: String,
    var firstname: String,
    var lastname: String,
    var address: Address,
    var phone: String,
    var email: String,
    var type: String // visitor, staff
)
data class UserData (
    var user: User
)
data class Address(
    var street: String?,
    var number: Int?,
    var city: String?
)

