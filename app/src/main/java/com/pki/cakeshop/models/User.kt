package com.pki.cakeshop.models

data class User(
    var username: String,
    var password: String,
    var firstname: String,
    var lastname: String,
    var address: Address,
    var phone: String,
    var email: String,
    var image: String, //ne treba
    var type: String // visitor, staff
)

data class Address(
    var street: String?,
    var number: Int?,
    var city: String?
)

