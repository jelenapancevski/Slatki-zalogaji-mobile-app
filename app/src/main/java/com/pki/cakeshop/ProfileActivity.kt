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

class ProfileActivity : AppCompatActivity() {
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile)
        userViewModel = UserViewModel()
        val pref = getSharedPreferences("data", Context.MODE_PRIVATE)
        val user:User = Gson().fromJson(pref.getString("user",null),User::class.java)
        if(user==null){
            //error
        }
        findViewById<TextView>(R.id.firstname).text=user.firstname
        findViewById<TextView>(R.id.lastname).text = user.lastname
        findViewById<TextView>(R.id.username).text = user.username
        findViewById<TextView>(R.id.address).text = "${user.address.street} ${user.address.number}, ${user.address.city}"
        findViewById<TextView>(R.id.phone).text = user.phone
        findViewById<TextView>(R.id.email).text = user.email

        val changepassword = findViewById<Button>(R.id.changepassword)
        val editdata = findViewById<Button>(R.id.editdata)
        changepassword.setOnClickListener {
            val intent = Intent(this@ProfileActivity, ChangePasswordActivity::class.java)
            startActivity(intent)
            return@setOnClickListener
        }
        editdata.setOnClickListener{
            val intent = Intent(this@ProfileActivity, EditDataActivity::class.java)
            startActivity(intent)
            return@setOnClickListener
        }
    }
}