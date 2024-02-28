package com.pki.cakeshop

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.gson.Gson
import com.pki.cakeshop.models.User
import com.pki.cakeshop.viewmodels.UserViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangePasswordActivity : AppCompatActivity() {
    private lateinit var userViewModel: UserViewModel
    private lateinit var users: List<User>
    // Function to check for at least one uppercase letter
    fun hasUpperCase(input: String): Boolean {
        val upperCaseRegex = Regex("[A-Z]+")
        return upperCaseRegex.containsMatchIn(input)
    }

    // Function to check for at least one special character
    fun hasSpecialCharacter(input: String): Boolean {
        val specialCharacterRegex = Regex("\\W+")
        return specialCharacterRegex.containsMatchIn(input)
    }

    // Function to check for at least one digit
    fun hasNumber(input: String): Boolean {
        val numberRegex = Regex("[0-9]+")
        return numberRegex.containsMatchIn(input)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.changepassword)
        userViewModel = UserViewModel()
        val pref = getSharedPreferences("data", Context.MODE_PRIVATE)
        val user: User = Gson().fromJson(pref.getString("user",null), User::class.java)
        if(user==null){
            //error
        }
        findViewById<Button>(R.id.cancel_button).setOnClickListener{
            val intent = Intent(this@ChangePasswordActivity, ProfileActivity::class.java)
            startActivity(intent)
            return@setOnClickListener
        }
        findViewById<EditText>(R.id.newpassword).addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                val password = s.toString()
                // val isValid = hasUpperCase(password) && hasSpecialCharacter(password) && hasNumber(password)

                if(!hasUpperCase(password)){
                    findViewById<EditText>(R.id.newpassword).error ="Lozinka mora sadržati bar jedno veliko slovo"
                }
                if(!hasSpecialCharacter(password)){
                    findViewById<EditText>(R.id.newpassword).error = " Lozinka mora sadržati bar jedan specijalni karakter"
                }
                if (!hasNumber(password)) {
                    findViewById<EditText>(R.id.newpassword).error = "Lozinka mora sadržati bar jedan broj"
                }
            }
        })

        findViewById<Button>(R.id.changepass).setOnClickListener{
            val currentpassword = findViewById<TextView>(R.id.currentpassword).text
            val newpassword = findViewById<TextView>(R.id.newpassword).text
            val confirmpassword = findViewById<TextView>(R.id.confirmpassword).text
            if(currentpassword.isNullOrBlank() || newpassword.isNullOrBlank() || confirmpassword.isNullOrBlank()){
                findViewById<TextView>(R.id.message).text = "Potrebno je uneti sve tražene podatke"
                return@setOnClickListener
            }
            if(currentpassword.toString()!=user.password){
                findViewById<TextView>(R.id.message).text = "Trenutna lozinka nije tačna"
                return@setOnClickListener
            }
            if(newpassword.toString()!=confirmpassword.toString()){
                findViewById<TextView>(R.id.message).text = "Lozinke se ne podudaraju"
                return@setOnClickListener
            }
            //check if password contains all the important elements
            val isValid = hasUpperCase(newpassword.toString()) && hasSpecialCharacter(newpassword.toString()) && hasNumber(newpassword.toString())
            if(!isValid){
                findViewById<TextView>(R.id.message).text= "Lozinka mora sadržati bar jedno veliko slovo, jedan specijalan karakter i jednu cifru"
                return@setOnClickListener
            }
            //valid change password
            userViewModel.changePassword(user._id,newpassword.toString(),object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    findViewById<TextView>(R.id.message).text= response.message()
                    // Logout user from application
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    findViewById<TextView>(R.id.message).text= "Greška prilikom promene lozinke"

                }

            })

        }
    }
}