package com.lastpro.taskmate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.content.ContentProviderCompat
import com.lastpro.taskmate.viewmodel.LoginViewModel

class MainActivity : AppCompatActivity() {

    val loginViewModel by viewModels<LoginViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loginViewModel.checkLogin(this ,{ isLogin ->
            if (!isLogin) {
                val intent = Intent(this, login::class.java)
                startActivity(intent)
            }
        })

        setContentView(R.layout.activity_main)
    }
}
