package com.pki.cakeshop

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.google.gson.Gson
import com.pki.cakeshop.models.Address
import com.pki.cakeshop.models.User
import com.pki.cakeshop.models.UserData
import com.pki.cakeshop.viewmodels.UserViewModel
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditDataActivity : MenuActivity() {
    private lateinit var user: User
    private lateinit var users: List<User>
    private lateinit var userViewModel: UserViewModel

    fun isEmailValid(email: String): Boolean {
        val emailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
        return emailRegex.toRegex().matches(email)
    }
    fun phoneNumber(input: String): Boolean {
        val phoneNumberRegex = Regex("^06[0-9]{7,8}\$")
        return phoneNumberRegex.containsMatchIn(input)
    }
    // check if number of street is correct pattern
    fun streetNumber(input: String): Boolean {
        val streetNumberRegex = Regex("^[1-9]+[0-9]*\$")
        return streetNumberRegex.containsMatchIn(input)
    }
    fun emailTaken(_id:String,email:String):Boolean{
        var found:Boolean=false
        users.forEach { user-> if(user.email==email && user._id!=_id) found=true }
        return found


    }
   fun usernameTaken(_id:String,username:String):Boolean{
        var found:Boolean=false
        users.forEach { user-> if(user.username==username && user._id!=_id) found=true }
        return found
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.editdata)
        userViewModel = UserViewModel()
        userViewModel.get(object: Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
               users = response.body()!!
            }
            override fun onFailure(call: Call<List<User>>, t: Throwable) {

            }

        })

        val pref = getSharedPreferences("data", Context.MODE_PRIVATE)
        user = Gson().fromJson(pref.getString("user",null),User::class.java)
        if(user==null){
            //error
        }
        findViewById<Button>(R.id.cancel_button).setOnClickListener{
            val intent = Intent(this@EditDataActivity, ProfileActivity::class.java)
            startActivity(intent)
            return@setOnClickListener
        }
        findViewById<TextView>(R.id.firstname).text=user.firstname
        findViewById<TextView>(R.id.lastname).text = user.lastname
        findViewById<TextView>(R.id.username).text = user.username
        findViewById<TextView>(R.id.city).text = user.address.city
        findViewById<TextView>(R.id.street).text = user.address.street
        findViewById<TextView>(R.id.number).text = user.address.number.toString()
        findViewById<TextView>(R.id.phone).text = user.phone
        findViewById<TextView>(R.id.email).text = user.email


        findViewById<Button>(R.id.updatedata).setOnClickListener{
            if(findViewById<TextView>(R.id.username).text.isNullOrBlank() || findViewById<TextView>(R.id.firstname).text.isNullOrBlank() ||
                findViewById<TextView>(R.id.lastname).text.isNullOrBlank() || findViewById<TextView>(R.id.street).text.isNullOrBlank() ||
                findViewById<TextView>(R.id.number).text.isNullOrBlank() || findViewById<TextView>(R.id.city).text.isNullOrBlank() || findViewById<TextView>(R.id.phone).text.isNullOrBlank() ||
                findViewById<TextView>(R.id.email).text.isNullOrBlank()){
                findViewById<TextView>(R.id.message).text = "Potrebno je uneti sve tražene podatke"
                return@setOnClickListener

            }
            //check if username is taken
           if(usernameTaken(user._id,findViewById<TextView>(R.id.username).text.toString())){
               findViewById<TextView>(R.id.message).text = "Korisničko ime je zauzeto"
               return@setOnClickListener
           }
            // check if number of street is correct pattern
            if(!streetNumber(findViewById<TextView>(R.id.number).text.toString())){
                findViewById<TextView>(R.id.number).error = "Pogrešan format"
                return@setOnClickListener
            }
            //phone number correct pattern
            if(!phoneNumber(findViewById<TextView>(R.id.phone).text.toString())){
                findViewById<TextView>(R.id.phone).error = "Pogrešan format e.g. 061123456"
                return@setOnClickListener
            }
            //email format and taken
            if(!isEmailValid(findViewById<TextView>(R.id.email).text.toString())){
                findViewById<TextView>(R.id.email).error = "Pogrešan format"
                return@setOnClickListener
            }

            if(emailTaken(user._id,findViewById<TextView>(R.id.email).text.toString())){
                findViewById<TextView>(R.id.message).text =  "Email adresa je zauzeta"
                return@setOnClickListener
            }

            //valid update data
            var newuser:User = User(
                user._id,
                findViewById<TextView>(R.id.username).text.toString(),
                user.password,
                findViewById<TextView>(R.id.firstname).text.toString(),
                findViewById<TextView>(R.id.lastname).text.toString(),
                Address(
                    findViewById<TextView>(R.id.street).text.toString(),
                    Integer.parseInt(findViewById<TextView>(R.id.number).text.toString()),
                    findViewById<TextView>(R.id.city).text.toString()
                ),
                findViewById<TextView>(R.id.phone).text.toString(),
                findViewById<TextView>(R.id.email).text.toString(),
                user.type)

            Log.e("USER", newuser.toString())
            userViewModel.edit(UserData(newuser),object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    findViewById<TextView>(R.id.message).text= newuser._id
                    // Return to profile and update user
                    userViewModel.user(user._id,object:Callback<User>{
                        override fun onResponse(call: Call<User>, response: Response<User>) {
                             val pref = getSharedPreferences("data", Context.MODE_PRIVATE)
                              val editor = pref.edit()
                              editor.putString("user", Gson().toJson(response.body()))
                              editor.apply()
                              val intent = Intent(this@EditDataActivity, ProfileActivity::class.java)
                              startActivity(intent)
                          }

                        override fun onFailure(call: Call<User>, t: Throwable) {
                            TODO("Not yet implemented")
                        }

                    })
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    findViewById<TextView>(R.id.message).text= "Greška prilikom promene podataka"

                }

            })
        }

    }
}