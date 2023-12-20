package com.lastpro.taskmate.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lastpro.taskmate.model.LoginResponse
import com.lastpro.taskmate.model.TaskLabel
import com.lastpro.taskmate.model.User
import com.lastpro.taskmate.network.ApiClient
import com.lastpro.taskmate.network.ApiService
import kotlinx.coroutines.launch

class TaskLabelViewModel : ViewModel() {

    private val _taskLabelResponse = MutableLiveData<List<TaskLabel>>()
    val taskLabelResponse: LiveData<List<TaskLabel>> get() = _taskLabelResponse

    fun getTaskLabel (context: Context){
        viewModelScope.launch {
            val preferences = context.getSharedPreferences("auth", Context.MODE_PRIVATE)
            val token   = preferences.getString("token","")
            try {
                val service : ApiService = ApiClient.apiService(token)
                val response = service.getTaskLabel()

                if(response.isSuccessful){
                    val body = response.body() as List<TaskLabel>
                    _taskLabelResponse.value = body
                }else{
                    Toast.makeText(context, response.errorBody()?.string().toString(), Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }
}
