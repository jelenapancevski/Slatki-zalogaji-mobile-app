package com.pki.cakeshop

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.gson.Gson
import com.pki.cakeshop.models.Address
import com.pki.cakeshop.models.User
import com.pki.cakeshop.models.UserData
import com.pki.cakeshop.viewmodels.UserViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditDataActivity : MenuActivity() {
    private lateinit var user: User
    private lateinit var users: List<User>
    private lateinit var userViewModel: UserViewModel
    private lateinit var username: TextView
    private lateinit var firstname: TextView
    private lateinit var lastname: TextView
    private lateinit var city: TextView
    private lateinit var street: TextView
    private lateinit var number: TextView
    private lateinit var phone: TextView
    private lateinit var email: TextView

    private fun isEmailValid(email: String): Boolean {
        val emailRegex = "^[A-Za-z](.*)(@)(.{1,})(\\.)(.{1,})"
        return emailRegex.toRegex().matches(email)
    }
    private fun phoneNumber(input: String): Boolean {
        val phoneNumberRegex = Regex("^06[0-9]{7,8}\$")
        return phoneNumberRegex.containsMatchIn(input)
    }
    // check if number of street is correct pattern
    private fun streetNumber(input: String): Boolean {
        val streetNumberRegex = Regex("^[1-9]+[0-9]*\$")
        return streetNumberRegex.containsMatchIn(input)
    }
    private fun emailTaken(id:String, email:String):Boolean{
        var found=false
        users.forEach { user-> if(user.email==email && user._id!=id) found=true }
        return found


    }
   private fun usernameTaken(id:String, username:String):Boolean{
        var found=false
        users.forEach { user-> if(user.username==username && user._id!=id) found=true }
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
        if(pref.getString("user",null)==null){
            //error
            Log.e("EditDataActivity", "Error user is null")
            return
        }
        user = Gson().fromJson(pref.getString("user",null),User::class.java)

        findViewById<Button>(R.id.cancel_button).setOnClickListener{
            val intent = Intent(this@EditDataActivity, ProfileActivity::class.java)
            startActivity(intent)
            return@setOnClickListener
        }
        firstname =  findViewById(R.id.firstname)
        firstname.text = user.firstname

        lastname = findViewById(R.id.lastname)
        lastname.text = user.lastname

        username = findViewById(R.id.username)
        username.text = user.username

        city = findViewById(R.id.city)
        city.text = user.address.city
        street = findViewById(R.id.street)
        street.text = user.address.street
        number = findViewById(R.id.number)
        number.text = user.address.number.toString()
        phone = findViewById(R.id.phone)
        phone.text = user.phone
        email = findViewById(R.id.email)
        email.text = user.email


        findViewById<Button>(R.id.updatedata).setOnClickListener{
            var foundError=false
            if(username.text.isNullOrBlank() || firstname.text.isNullOrBlank() ||
                lastname.text.isNullOrBlank() || street.text.isNullOrBlank() ||
                number.text.isNullOrBlank() || city.text.isNullOrBlank() || phone.text.isNullOrBlank() ||
                email.text.isNullOrBlank()){
                findViewById<TextView>(R.id.message).text = getString(R.string.edit_data_not_provided_error_message)
                foundError = true


            }
            //check if username is taken
           if(usernameTaken(user._id,findViewById<TextView>(R.id.username).text.toString())){
               findViewById<TextView>(R.id.message).text = getString(R.string.username_taken_message)
               foundError = true
           }
            // check if number of street is correct pattern
            if(!streetNumber(findViewById<TextView>(R.id.number).text.toString())){
                number.error = getString(R.string.address_format_message)
                foundError = true
            }
            //phone number correct pattern
            if(!phoneNumber(findViewById<TextView>(R.id.phone).text.toString())){
                phone.error = getString(R.string.phone_format_message)
                foundError = true
            }
            //email format and taken
            if(!isEmailValid(findViewById<TextView>(R.id.email).text.toString())){
                email.error = getString(R.string.email_format_message)
                foundError = true
            }

            if(emailTaken(user._id,findViewById<TextView>(R.id.email).text.toString())){
                findViewById<TextView>(R.id.message).text =  getString(R.string.email_taken_message)
                foundError = true
            }
            if(foundError) return@setOnClickListener

            //valid update data
            val newUser = User(
                user._id,
                username.text.toString(),
                user.password,
               firstname.text.toString(),
                lastname.text.toString(),
                Address(
                   street.text.toString(),
                    Integer.parseInt(number.text.toString()),
                    city.text.toString()
                ),
                phone.text.toString(),
                email.text.toString(),
                user.type)


            userViewModel.edit(UserData(newUser),object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    findViewById<TextView>(R.id.message).text= newUser._id
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
                    findViewById<TextView>(R.id.message).text= getString(R.string.data_change_error)

                }

            })
        }

    }
    override fun onRestart() {
        super.onRestart()
        firstname.clearFocus()
        lastname.clearFocus()
        username.clearFocus()
        city.clearFocus()
        street.clearFocus()
        number.clearFocus()
        phone.clearFocus()
        email.clearFocus()

        firstname.text=user.firstname
        lastname.text = user.lastname
        username.text = user.username
        city.text = user.address.city
        street.text = user.address.street
        number.text = user.address.number.toString()
        phone.text = user.phone
        email.text = user.email

    }
}