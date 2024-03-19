package com.pki.cakeshop

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.gson.Gson
import com.pki.cakeshop.models.User
import com.pki.cakeshop.viewmodels.UserViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangePasswordActivity : MenuActivity() {
    private lateinit var userViewModel: UserViewModel

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
        if(pref.getString("user",null)==null){
            //error
            Log.e("ChangePasswordActivity", "Error user is null")
            return
        }
        val user: User = Gson().fromJson(pref.getString("user",null), User::class.java)

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
                    findViewById<EditText>(R.id.newpassword).error = getString(R.string.password_one_upper_case_error)
                }
                if(!hasSpecialCharacter(password)){
                    findViewById<EditText>(R.id.newpassword).error = getString(R.string.password_one_special_character_error)
                }
                if (!hasNumber(password)) {
                    findViewById<EditText>(R.id.newpassword).error = getString(R.string.password_one_number_error)
                }
            }
        })

        findViewById<Button>(R.id.changepass).setOnClickListener{
            val currentPassword = findViewById<TextView>(R.id.currentpassword).text
            val newPassword = findViewById<TextView>(R.id.newpassword).text
            val confirmPassword = findViewById<TextView>(R.id.confirmpassword).text
            if(currentPassword.isNullOrBlank() || newPassword.isNullOrBlank() || confirmPassword.isNullOrBlank()){
                findViewById<TextView>(R.id.message).text = getString(R.string.edit_data_not_provided_error_message)
                return@setOnClickListener
            }
            if(currentPassword.toString()!=user.password){
                findViewById<TextView>(R.id.message).text = getString(R.string.current_password_incorrect_message)
                return@setOnClickListener
            }
            if(newPassword.toString()!=confirmPassword.toString()){
                findViewById<TextView>(R.id.message).text = getString(R.string.passwords_dont_match_message)
                return@setOnClickListener
            }
            //check if password contains all the important elements
            val isValid = hasUpperCase(newPassword.toString()) && hasSpecialCharacter(newPassword.toString()) && hasNumber(newPassword.toString())
            if(!isValid){
                findViewById<TextView>(R.id.message).text= getString(R.string.password_contains_characters_message)
                return@setOnClickListener
            }
            //valid change password
            userViewModel.changePassword(user._id,newPassword.toString(),object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    findViewById<TextView>(R.id.message).text= getString(R.string.password_changed_message)
                    // Logout user from application
                    val pref = getSharedPreferences("data", Context.MODE_PRIVATE)
                    val edit = pref.edit()
                    edit.remove("order")
                    edit.remove("user")
                    edit.remove("product")
                    edit.remove("product_image")
                    val intent = Intent(this@ChangePasswordActivity, MainActivity::class.java).apply {
                    }
                    startActivity(intent)
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    findViewById<TextView>(R.id.message).text= getString(R.string.password_change_error)

                }

            })

        }
    }
}