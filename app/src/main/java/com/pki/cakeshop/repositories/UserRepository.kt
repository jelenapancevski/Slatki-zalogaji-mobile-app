package com.pki.cakeshop.repositories

import android.util.Log
import com.google.gson.Gson
import com.pki.cakeshop.models.User
import com.pki.cakeshop.services.UserService
import org.bson.types.ObjectId
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

class UserRepository(private val userService: UserService) {
    // returns all users
    fun get(callback: Callback<List<User>>){
        userService.get().enqueue(callback)
    }
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
        userService.user(id).enqueue(callback)
    }
}