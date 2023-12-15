package com.lastpro.taskmate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.lastpro.taskmate.viewmodel.LoginViewModel

class login : AppCompatActivity() {
    private val viewModel: LoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val usernameEditText : EditText = findViewById(R.id.signInUsername)
        val passwordEditText : EditText = findViewById(R.id.signInPassword)

        val loginButton : Button = findViewById(R.id.buttonLogin)
        loginButton.setOnClickListener{
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            viewModel.login(username, password)
        }
    }
}