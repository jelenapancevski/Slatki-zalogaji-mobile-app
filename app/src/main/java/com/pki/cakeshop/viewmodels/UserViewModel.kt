package com.pki.cakeshop.viewmodels
import androidx.lifecycle.ViewModel
import com.pki.cakeshop.RetrofitClient
import com.pki.cakeshop.models.User
import com.pki.cakeshop.models.UserData
import com.pki.cakeshop.repositories.UserRepository
import retrofit2.Callback
 class UserViewModel : ViewModel() {

     private val userRepository=UserRepository(RetrofitClient.userService)

     fun get(callback: Callback<List<User>>){
         userRepository.get(callback)
     }
     fun login( username:String,password:String, callback: Callback<User>) {
         userRepository.login(username,password,callback)
     }

     // change password
     fun changePassword(id: String, newPassword:String, callback: Callback<String>) {
         userRepository.changePassword(id,newPassword,callback)
     }
     // edit personal info
     fun edit(user: UserData, callback: Callback<String>) {
         userRepository.edit(user,callback)
     }
     // get user
     fun user(id: String, callback: Callback<User>) {
         userRepository.user(id,callback)
     }

 }
