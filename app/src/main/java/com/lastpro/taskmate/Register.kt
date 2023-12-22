package com.lastpro.taskmate

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import com.lastpro.taskmate.viewmodel.LoginViewModel
import com.lastpro.taskmate.viewmodel.RegisterViewModel

class Register : AppCompatActivity() {
    val loginViewModel by viewModels<LoginViewModel>()
    val registerViewModel by viewModels<RegisterViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        loginViewModel.checkLogin(this,{ isLogin ->
            if(isLogin){
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        })

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val sharedPreferences = getSharedPreferences("auth", MODE_PRIVATE)
        val myEdit: SharedPreferences.Editor = sharedPreferences.edit()

        val usernameEditText : EditText = findViewById(R.id.signUpUsername)
        val passwordEditText : EditText = findViewById(R.id.signUpPassword)

        val registerButton : Button = findViewById(R.id.buttonRegister)
        registerButton.setOnClickListener{
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            registerViewModel.register(username, password, { data ->
                myEdit.putString("token",data.token)
                myEdit.putBoolean("is_login",true)
                myEdit.apply()

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }, {error ->
                Toast.makeText(this,error, Toast.LENGTH_SHORT).show()
            })

        }

        val loginButton : TextView = findViewById(R.id.buttonToLogin)
        loginButton.setOnClickListener{
            val intent = Intent(this, login::class.java)
            startActivity(intent)
        }
    }
}
