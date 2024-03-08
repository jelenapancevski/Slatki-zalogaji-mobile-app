package com.pki.cakeshop

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import retrofit2.Call
import com.pki.cakeshop.models.User
import com.pki.cakeshop.viewmodels.UserViewModel
import retrofit2.Response
import org.bson.types.ObjectId
import org.w3c.dom.Text
import retrofit2.Callback

class MainActivity : AppCompatActivity() {
    private lateinit var userViewModel: UserViewModel
    private lateinit var message: TextView
    private lateinit var username: TextInputEditText
    private lateinit var password: TextInputEditText
    private lateinit var loginButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        message = findViewById(R.id.message);
        username = findViewById(R.id.username)
        password = findViewById(R.id.password)
        loginButton = findViewById(R.id.loginButton)
        userViewModel = UserViewModel();

        loginButton.setOnClickListener {
            if (username.text.isNullOrBlank() || password.text.isNullOrBlank()) {
                message.text = "Neophodno je uneti korisničko ime i lozinku"

            }
            else {
                userViewModel.login(username.text.toString(),password.text.toString(), object : Callback<User> {
                    override fun onResponse(call: Call<User>, response: Response<User>) {
                        if (response.isSuccessful) {
                            val user = response.body()
                            if (user != null) {
                                if(user.type=="visitor"){
                                    // go to homepage and save the logged in user
                                    val pref = getSharedPreferences("data", Context.MODE_PRIVATE)
                                    val editor = pref.edit()
                                    editor.putString("user", Gson().toJson(user))
                                    editor.apply()
                                    val intent = Intent(this@MainActivity, DessertsActivity::class.java)
                                    startActivity(intent)
                                }
                                else {
                                    message.text="Uneti kredencijali nisu validni"
                                }
                                message.text=user.username;
                            } else {
                                message.text="Uneti kredencijali nisu validni"
                            }
                        } else {
                            message.text="Greška prilikom prijave"
                        }
                    }

                    override fun onFailure(call: Call<User>, t: Throwable) {
                        // Handle failure
                        message.text="Uneti kredencijali nisu validni"
                    }
                })

            }
        }


    }

    }
