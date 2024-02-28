package com.pki.cakeshop.viewmodels
import androidx.lifecycle.ViewModel
import com.pki.cakeshop.RetrofitClient
import com.pki.cakeshop.models.User
import com.pki.cakeshop.repositories.UserRepository
import org.bson.types.ObjectId
import retrofit2.Callback
 class UserViewModel() : ViewModel() {

     private val userRepository=UserRepository(RetrofitClient.userService);
     fun login( username:String,password:String, callback: Callback<User>) {
         userRepository.login(username,password,callback)
     }
     fun checkavailability(username:String, email:String, callback: Callback<List<User>>) {
         userRepository.checkavailability(username,email,callback)
     }
     // change password
     fun changePassword(id: String, newpassword:String, callback: Callback<String>) {
         userRepository.changePassword(id,newpassword,callback)
     }
     // edit personal info
     fun edit(user: User, callback: Callback<String>) {
         userRepository.edit(user,callback)
     }
     // get user
     fun user(id: String, callback: Callback<User>) {
         userRepository.user(id,callback)
     }

 }
