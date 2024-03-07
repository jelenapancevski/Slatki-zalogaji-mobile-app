package com.pki.cakeshop.services
import com.google.gson.Gson
import com.pki.cakeshop.models.Product
import com.pki.cakeshop.models.User
import com.pki.cakeshop.models.UserData
import okhttp3.RequestBody
import org.bson.types.ObjectId
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
interface UserService {
    // returns all users
    @GET("user/get")
    fun get() : Call<List<User>>

    @FormUrlEncoded
    @POST("user/login")
    fun login(@Field("username")username:String, @Field("password") password:String): Call<User>

    // check if username and email are unique

    @FormUrlEncoded
    @POST("user/checkavailability")
    fun checkavailability(@Field("username") username: String, @Field("email") email: String): Call<List<User>>

    // change password
    @FormUrlEncoded
    @POST("user/changePassword")
    fun changePassword(@Field("id")id:String, @Field("newpassword") newpassword:String):Call<String>

    // edit personal info
    @POST("user/edit")
    fun edit(@Body user:UserData):Call<String>

    // get user
    @FormUrlEncoded
    @POST("user/user")
    fun user(@Field("id") id:String):Call<User>

}

