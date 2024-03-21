package com.pki.cakeshop

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.google.gson.Gson
import com.pki.cakeshop.models.User
import com.pki.cakeshop.viewmodels.UserViewModel

class ProfileActivity : MenuActivity() {
    private lateinit var userViewModel: UserViewModel
    private lateinit var user:User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile)
        userViewModel = UserViewModel()
        val pref = getSharedPreferences("data", Context.MODE_PRIVATE)
        if(pref.getString("user",null)==null){
            //error
            Log.e("ProfileActivity", "Error user is null")
            return
        }
        user = Gson().fromJson(pref.getString("user",null),User::class.java)

        findViewById<TextView>(R.id.firstname).text=user.firstname
        findViewById<TextView>(R.id.lastname).text = user.lastname
        findViewById<TextView>(R.id.username).text = user.username
        findViewById<TextView>(R.id.address).text = getString(R.string.address_format,user.address.street,user.address.number.toString(),user.address.city)
        findViewById<TextView>(R.id.phone).text = user.phone
        findViewById<TextView>(R.id.email).text = user.email

        val changePassword = findViewById<Button>(R.id.changepassword)
        val editData = findViewById<Button>(R.id.editdata)
        changePassword.setOnClickListener {
            val intent = Intent(this@ProfileActivity, ChangePasswordActivity::class.java)
            startActivity(intent)
            return@setOnClickListener
        }
        editData.setOnClickListener{
            val intent = Intent(this@ProfileActivity, EditDataActivity::class.java)
            startActivity(intent)
            return@setOnClickListener
        }
    }


}