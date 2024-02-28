package com.pki.cakeshop.repositories

import android.util.Log
import com.google.gson.Gson
import com.pki.cakeshop.models.User
import com.pki.cakeshop.services.UserService
import org.bson.types.ObjectId
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.Body
import retrofit2.http.POST

class UserRepository(private val userService: UserService) {

    fun login( username:String,password:String, callback: Callback<User>) {
        userService.login(username,password).enqueue(callback)
    }
    fun checkavailability(username:String, email:String, callback: Callback<List<User>>) {
        userService.checkavailability(username,email).enqueue(callback)
    }
    // change password
    fun changePassword(id:String, newpassword:String, callback: Callback<String>) {
        userService.changePassword(id,newpassword).enqueue(callback)
    }
    // edit personal info
    fun edit(user:User, callback: Callback<String>) {
        userService.edit(user).enqueue(callback)
    }
    // get user
    fun user(id: String, callback: Callback<User>) {
        // Create a map representing the request body data
     //   val requestData = mapOf("id" to id)

        // Convert the request body data to JSON string
       // val requestBody = Gson().toJson(requestData)

        // Log the JSON data before making the Retrofit call
        //Log.d("RequestData", "User Request Body: $requestBody")
        userService.user(id).enqueue(callback)
    }
}