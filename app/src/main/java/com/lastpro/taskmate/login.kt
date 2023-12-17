package com.lastpro.taskmate

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.lastpro.taskmate.viewmodel.LoginViewModel


class login : AppCompatActivity() {
//    private val viewModel: LoginViewModel by viewModels()
    val viewModel by viewModels<LoginViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPreferences = getSharedPreferences("auth", MODE_PRIVATE)
        if(sharedPreferences.getBoolean("is_login",false)) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        val myEdit: SharedPreferences.Editor = sharedPreferences.edit()

        setContentView(R.layout.activity_login)

        val usernameEditText : EditText = findViewById(R.id.signInUsername)
        val passwordEditText : EditText = findViewById(R.id.signInPassword)

        val loginButton : Button = findViewById(R.id.buttonLogin)
        loginButton.setOnClickListener{
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            viewModel.login(username, password, { data ->
                myEdit.putString("token",data.token)
                myEdit.putBoolean("is_login",true)
                myEdit.apply()

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }, {error ->
                Toast.makeText(this,error,Toast.LENGTH_SHORT).show()
            })

        }

        val registerButton : TextView = findViewById(R.id.buttonRegister)
        registerButton.setOnClickListener{
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }


    }
}
