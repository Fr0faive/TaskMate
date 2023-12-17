package com.lastpro.taskmate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContentProviderCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPreferences = getSharedPreferences("auth", MODE_PRIVATE)
        if(!sharedPreferences.getBoolean("is_login",false)){
            val intent = Intent(this, login::class.java)
            startActivity(intent)
        }
        /*val myEdit = sharedPreferences.edit()
        val token = sharedPreferences?.getString("token","")
        val is_login = sharedPreferences?.getBoolean("is_login",false)*/

        setContentView(R.layout.activity_main)
    }
}
