package com.lastpro.taskmate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lastpro.taskmate.adapter.TaskLabelAdapter
import com.lastpro.taskmate.adapter.TasksAdapter
import com.lastpro.taskmate.viewmodel.LoginViewModel
import com.lastpro.taskmate.viewmodel.TaskLabelViewModel
import com.lastpro.taskmate.viewmodel.TasksViewModel

class Tasks : AppCompatActivity() {
    val loginViewModel by viewModels<LoginViewModel>()
    val taskLabelViewModel by viewModels<TaskLabelViewModel> ()
    val tasksViewModel by viewModels<TasksViewModel> ()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginViewModel.checkLogin(this ,{ isLogin ->
            if (!isLogin) {
                val intent = Intent(this, login::class.java)
                startActivity(intent)
            }
        })
        setContentView(R.layout.activity_tasks)

        val extras = intent.extras
        val tasklabel_id = extras!!.getInt("tasklabel_id")
        val title : TextView = findViewById(R.id.title_task)
        taskLabelViewModel.getByIdTaskLabel(this,tasklabel_id,{data ->
            title.setText(data.name)
        })

        val tasks_con = findViewById<RecyclerView>(R.id.container_tasks)
        tasks_con.layoutManager = LinearLayoutManager(this)

        tasksViewModel.getTasks(this,tasklabel_id)
        tasksViewModel.tasksResponse.observe(this, Observer { data ->
            val adapter = TasksAdapter(data, onEdit = { id ->
                val intent = Intent(this, TaskEdit::class.java)
                intent.putExtra("id",id)
                intent.putExtra("tasklabel_id",tasklabel_id)
                startActivity(intent)
            }, onDelete = { id ->
                val builder = AlertDialog.Builder(this)
                builder.setMessage("Are you sure you want to Delete?")
                    .setCancelable(false)
                    .setPositiveButton("Yes") { dialog, response ->
                        tasksViewModel.deleteTask(this,id, { onSuccess ->
                            tasksViewModel.getTasks(this,tasklabel_id)
                        })
                    }
                    .setNegativeButton("No") { dialog, id ->
                        dialog.dismiss()
                    }
                val alert = builder.create()
                alert.show()
            })
            tasks_con.adapter = adapter
        })

        val addTaskButton : View = findViewById(R.id.button_add_task)
        addTaskButton.setOnClickListener{
            val intent = Intent(this, TaskEdit::class.java)
            intent.putExtra("tasklabel_id",tasklabel_id)
            startActivity(intent)
        }

        val backTaskButton : TextView = findViewById(R.id.button_back_tasklabel_from_tasks)
        backTaskButton.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
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
