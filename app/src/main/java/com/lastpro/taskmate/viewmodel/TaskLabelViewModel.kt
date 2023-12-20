package com.lastpro.taskmate.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lastpro.taskmate.model.ApiResponse
import com.lastpro.taskmate.model.LoginResponse
import com.lastpro.taskmate.model.TaskLabel
import com.lastpro.taskmate.model.User
import com.lastpro.taskmate.network.ApiClient
import com.lastpro.taskmate.network.ApiService
import kotlinx.coroutines.launch
import org.json.JSONObject

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
                    val jObjError = JSONObject(response.errorBody()!!.string())
                    val message     = jObjError.getString("message")
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun insertTaskLabel (context: Context, data : TaskLabel, onSuccess : (Boolean)->Unit){
        viewModelScope.launch {
            val preferences = context.getSharedPreferences("auth", Context.MODE_PRIVATE)
            val token   = preferences.getString("token","")
            try {
                val service : ApiService = ApiClient.apiService(token)
                val response = service.insertTaskLabel(data)

                if(response.isSuccessful){
                    val body = response.body() as ApiResponse
                    Toast.makeText(context, body.message, Toast.LENGTH_SHORT).show()
                    onSuccess(true)
                }else{
                    val jObjError = JSONObject(response.errorBody()!!.string())
                    val message     = jObjError.getString("message")
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    onSuccess(false)
                }
            } catch (e: Exception) {
                Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show()
                onSuccess(false)
            }
        }
    }

    fun updateTaskLabel (context: Context, data : TaskLabel, onSuccess : (Boolean)->Unit){
        viewModelScope.launch {
            val preferences = context.getSharedPreferences("auth", Context.MODE_PRIVATE)
            val token   = preferences.getString("token","")
            try {
                val service : ApiService = ApiClient.apiService(token)
                val response = service.updateTaskLabel(data.id,data)

                if(response.isSuccessful){
                    val body = response.body() as ApiResponse
                    Toast.makeText(context, body.message, Toast.LENGTH_SHORT).show()
                    onSuccess(true)
                }else{
                    val jObjError = JSONObject(response.errorBody()!!.string())
                    val message     = jObjError.getString("message")
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    onSuccess(false)
                }
            } catch (e: Exception) {
                Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show()
                onSuccess(false)
            }
        }
    }
}
