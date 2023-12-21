package com.lastpro.taskmate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels
import com.lastpro.taskmate.model.Task
import com.lastpro.taskmate.model.TaskLabel
import com.lastpro.taskmate.viewmodel.LoginViewModel
import com.lastpro.taskmate.viewmodel.TaskLabelViewModel
import com.lastpro.taskmate.viewmodel.TasksViewModel

class TaskEdit : AppCompatActivity() {

    val loginViewModel by viewModels<LoginViewModel>()
    val tasksViewModel by viewModels<TasksViewModel> ()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loginViewModel.checkLogin(this ,{ isLogin ->
            if (!isLogin) {
                val intent = Intent(this, login::class.java)
                startActivity(intent)
            }
        })

        setContentView(R.layout.activity_task_edit)

        val title : EditText = findViewById(R.id.input_title_task)
        val dueDate : EditText = findViewById(R.id.input_dueDate_task)
        val description : EditText = findViewById(R.id.input_description_task)
        var id = 0
        val extras = intent.extras
        val tasklabel_id = extras!!.getInt("tasklabel_id")

        if (extras != null) {
            id = extras.getInt("id")
            if(!id.equals(0)){
                tasksViewModel.getByIdTask(this,id,{data ->
                    title.setText(data.title)
                    description.setText(data.description)
                    dueDate.setText(data.dueDate)
                })
            }
        }

        val backTaskButton : TextView = findViewById(R.id.button_back_task)
        backTaskButton.setOnClickListener{
            val intent = Intent(this, Tasks::class.java)
            intent.putExtra("tasklabel_id",tasklabel_id)
            startActivity(intent)
        }

        val submitTaskButton : Button = findViewById(R.id.button_task_submit)
        submitTaskButton.setOnClickListener{

            val data = Task(id,tasklabel_id, title.text.toString(),description.text.toString(),dueDate.text.toString())
            submitTaskButton.isEnabled = false
            submitTaskButton.isClickable = false

            if(!id.equals(0)){
                tasksViewModel.updateTask(this, data , { onSuccess ->
                    submitTaskButton.isEnabled = true
                    submitTaskButton.isClickable = true
                    if(onSuccess){
                        val intent = Intent(this, Tasks::class.java)
                        intent.putExtra("tasklabel_id",tasklabel_id)
                        startActivity(intent)
                    }
                })
            }else{
                tasksViewModel.insertTask(this, data , { onSuccess ->
                    submitTaskButton.isEnabled = true
                    submitTaskButton.isClickable = true
                    if(onSuccess){
                        val intent = Intent(this, Tasks::class.java)
                        intent.putExtra("tasklabel_id",tasklabel_id)
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
