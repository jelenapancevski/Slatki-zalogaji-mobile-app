package com.pki.cakeshop.services
import com.pki.cakeshop.models.Product
import com.pki.cakeshop.models.User
import org.bson.types.ObjectId
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
interface UserService {
    @FormUrlEncoded
    @POST("user/login")
    fun login(@Field("username")username:String, @Field("password") password:String): Call<User>

    // check if username and email are unique
    @Headers("Content-Type: application/json")
    @FormUrlEncoded
    @POST("user/checkavailability")
    fun checkavailability(@Field("username") username: String, @Field("email") email: String): Call<List<User>>

    // change password
    @Headers("Content-Type: application/json")
    @FormUrlEncoded
    @POST("user/changePassword")
    fun changePassword(@Field("id")id:String, @Field("newpassword") newpassword:String):Call<String>

    // edit personal info
    @Headers("Content-Type: application/json")
    @POST("user/edit")
    fun edit(@Body user:User):Call<String>

    // get user
    @FormUrlEncoded
    @POST("user/user")
    fun user(@Field("id") id:String):Call<User>

}

