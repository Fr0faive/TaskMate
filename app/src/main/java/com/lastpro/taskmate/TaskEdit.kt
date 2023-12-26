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
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class TaskEdit : AppCompatActivity() {

    val loginViewModel by viewModels<LoginViewModel>()
    val tasksViewModel by viewModels<TasksViewModel> ()
    private lateinit var dueDateEditText: EditText
    private val calendar = Calendar.getInstance()

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
//        val dueDate : EditText = findViewById(R.id.input_dueDate_task)
        dueDateEditText = findViewById(R.id.input_dueDate_task)
        dueDateEditText.setOnClickListener {
            showDateTimePicker()
        }
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
                    dueDateEditText.setText(data.dueDate)
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

            val data = Task(id,tasklabel_id, title.text.toString(),description.text.toString(),dueDateEditText.text.toString())
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

    private fun showDateTimePicker() {
        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                // Set the selected date to the calendar
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                // Show TimePickerDialog after selecting the date
                showTimePicker()
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        datePickerDialog.show()
    }

    private fun showTimePicker() {
        val timePickerDialog = TimePickerDialog(
            this,
            TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                // Set the selected time to the calendar
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calendar.set(Calendar.MINUTE, minute)

                // Format the selected date and time
                val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
                val formattedDateTime = dateFormat.format(calendar.time)

                // Set the formatted date and time to the EditText
                dueDateEditText.setText(formattedDateTime)
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        )

        timePickerDialog.show()
    }
}
