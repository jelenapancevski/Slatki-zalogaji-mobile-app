package com.pki.cakeshop.repositories

import com.pki.cakeshop.models.User
import com.pki.cakeshop.models.UserData
import com.pki.cakeshop.services.UserService
import retrofit2.Callback

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
    fun edit(user:UserData, callback: Callback<String>) {
        userService.edit(user).enqueue(callback)
    }
    // get user
    fun user(id: String, callback: Callback<User>) {
        userService.user(id).enqueue(callback)
    }
}