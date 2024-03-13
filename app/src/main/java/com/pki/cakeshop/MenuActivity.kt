package com.pki.cakeshop

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu

open class MenuActivity : AppCompatActivity() {

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.mainmenu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            /*R.id.action_products->{
                 showProductsMenu()
                true
            }*/
            R.id.action_cakes -> {
                val intent = Intent(this,
                    CakesActivity::class.java).apply {
                        putExtra("type", "torta")
                }
                startActivity(intent)
                true
            }
            R.id.action_desserts -> {
                val intent = Intent(this,
                    CakesActivity::class.java).apply {
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
                val intent = Intent(this,
                    MainActivity::class.java).apply {
                }
                startActivity(intent)

                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    /*private fun showProductsMenu() {
        val popupMenu = PopupMenu(this, findViewById(R.id.action_products) )
        popupMenu.menuInflater.inflate(R.menu.productsmenu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { menuItem ->
            when(menuItem.itemId) {
                R.id.action_cakes -> {
                    val intent = Intent(this,
                        CakesActivity::class.java).apply {
                    }
                    startActivity(intent)
                    true
                }
                R.id.action_desserts -> {
                    val intent = Intent(this,
                        DessertsActivity::class.java).apply {
                    }
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
    }
*/
}