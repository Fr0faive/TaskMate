package com.lastpro.taskmate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContentProviderCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lastpro.taskmate.adapter.TaskLabelAdapter
import com.lastpro.taskmate.model.TaskLabel
import com.lastpro.taskmate.viewmodel.LoginViewModel
import com.lastpro.taskmate.viewmodel.TaskLabelViewModel

class MainActivity : AppCompatActivity() {

    val loginViewModel by viewModels<LoginViewModel>()
    val taskLabelViewModel by viewModels<TaskLabelViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loginViewModel.checkLogin(this ,{ isLogin ->
            if (!isLogin) {
                val intent = Intent(this, login::class.java)
                startActivity(intent)
            }
        })

        setContentView(R.layout.activity_main)

        val tasklabel_con = findViewById<RecyclerView>(R.id.container_tasklabel)
        tasklabel_con.layoutManager = LinearLayoutManager(this)

        taskLabelViewModel.getTaskLabel(this)
        taskLabelViewModel.taskLabelResponse.observe(this, Observer { data ->
            val adapter = TaskLabelAdapter(data)
            tasklabel_con.adapter = adapter
        })

        val addTaskLabelButton : View = findViewById(R.id.button_add_tasklabel)
        addTaskLabelButton.setOnClickListener{
            val intent = Intent(this, TasklabelEdit::class.java)
            startActivity(intent)
        }
    }
}
