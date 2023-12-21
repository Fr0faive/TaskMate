package com.lastpro.taskmate

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.lastpro.taskmate.model.TaskLabel
import com.lastpro.taskmate.viewmodel.LoginViewModel
import com.lastpro.taskmate.viewmodel.TaskLabelViewModel

class TasklabelEdit : AppCompatActivity() {

    val loginViewModel by viewModels<LoginViewModel>()
    val taskLabelViewModel by viewModels<TaskLabelViewModel> ()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loginViewModel.checkLogin(this ,{ isLogin ->
            if (!isLogin) {
                val intent = Intent(this, login::class.java)
                startActivity(intent)
            }
        })

        setContentView(R.layout.activity_tasklabel_edit)

        val title : EditText = findViewById(R.id.input_title_tasklabel)
        var id = 0
        val extras = intent.extras
        if (extras != null) {
            id = extras.getInt("id")

            taskLabelViewModel.getByIdTaskLabel(this,id,{data ->
                title.setText(data.name)
            })
        }


        val backTaskLabelButton : Button = findViewById(R.id.button_back_tasklabel)
        backTaskLabelButton.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val submitTaskLabelButton : Button = findViewById(R.id.button_tasklabel_submit)
        submitTaskLabelButton.setOnClickListener{

            val data = TaskLabel(id, title.text.toString())
            submitTaskLabelButton.isEnabled = false
            submitTaskLabelButton.isClickable = false

            if(!id.equals(0)){
                taskLabelViewModel.updateTaskLabel(this, data , { onSuccess ->
                    submitTaskLabelButton.isEnabled = true
                    submitTaskLabelButton.isClickable = true
                    if(onSuccess){
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }
                })
            }else{
                taskLabelViewModel.insertTaskLabel(this, data , { onSuccess ->
                    submitTaskLabelButton.isEnabled = true
                    submitTaskLabelButton.isClickable = true
                    if(onSuccess){
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }
                })
            }

        }

        val logoutButton : TextView = findViewById(R.id.button_logout)
        logoutButton.setOnClickListener{
            loginViewModel.logout(this,{onSuccess ->
                val intent = Intent(this, login::class.java)
                startActivity(intent)
            })
        }
    }
}
