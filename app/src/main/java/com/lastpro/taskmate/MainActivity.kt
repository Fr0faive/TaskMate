package com.lastpro.taskmate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.content.ContentProviderCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lastpro.taskmate.adapter.TaskLabelAdapter
import com.lastpro.taskmate.model.TaskLabel
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

        val tasklabel_con = findViewById<RecyclerView>(R.id.container_tasklabel)
        tasklabel_con.layoutManager = LinearLayoutManager(this)

        val data = ArrayList<TaskLabel>()
        for (i in 1..20) {
            data.add(TaskLabel(i,1,"Test " + i))
        }

        val adapter = TaskLabelAdapter(data)
        tasklabel_con.adapter = adapter
    }
}
