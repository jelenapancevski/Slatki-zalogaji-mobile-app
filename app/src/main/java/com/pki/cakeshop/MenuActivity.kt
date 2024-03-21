package com.pki.cakeshop
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat


open class MenuActivity : AppCompatActivity() {

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.mainmenu, menu)
        supportActionBar?.setDisplayShowCustomEnabled(true)
        supportActionBar?.setCustomView(R.layout.title_layout)
        supportActionBar?.customView?.findViewById<TextView>(R.id.title_home)?.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }
        supportActionBar?.customView?.findViewById<TextView>(R.id.title_home)?.apply {
            textSize = 35f
            typeface = ResourcesCompat.getFont(context, R.font.inspiration)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_cakes -> {
                val intent = Intent(this,
                    ProductsActivity::class.java).apply {
                        putExtra("type", "torta")
                }
                startActivity(intent)
                true
            }
            R.id.action_desserts -> {
                val intent = Intent(this,
                    ProductsActivity::class.java).apply {
                    putExtra("type", "kolač")
                }
                startActivity(intent)
                true
            }
            R.id.action_promotions->{
                val intent = Intent(this,
                    PromotionsActivity::class.java).apply {
                }
                startActivity(intent)

                return true
            }
            R.id.action_contact->{
                val intent = Intent(this,
                    ContactActivity::class.java).apply {
                }
                startActivity(intent)

                return true
            }
            R.id.action_basket->{
                val intent = Intent(this,
                    BasketActivity::class.java).apply {
                }
                startActivity(intent)
                return true
            }
            R.id.action_profile->{
                val intent = Intent(this,
                    ProfileActivity::class.java).apply {
                }
                startActivity(intent)
                return true
            }
            R.id.action_notifications->{
                val intent = Intent(this,
                    NotificationsActivity::class.java).apply {
                }
                startActivity(intent)
                return true
            }
            R.id.action_logout->{
                val pref = getSharedPreferences("data", Context.MODE_PRIVATE)
                val edit = pref.edit()
                edit.remove("order")
                edit.remove("user")
                edit.remove("product")
                edit.remove("product_image")
                edit.apply()
                val intent = Intent(this,
                    MainActivity::class.java).apply {
                }
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)

                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}